package com.cosmoclicker.app.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.cosmoclicker.app.data.model.ClickPattern
import com.cosmoclicker.app.data.model.PatternType

class AutoClickerService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var currentPattern: ClickPattern? = null
    private var clickCount = 0
    private var totalClicks = 0

    companion object {
        @Volatile
        var instance: AutoClickerService? = null

        fun isRunning(): Boolean = instance?.isRunning == true

        fun getInstance(): AutoClickerService? = instance
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        stopClicking()
    }

    fun startClicking(pattern: ClickPattern) {
        if (isRunning) return

        isRunning = true
        currentPattern = pattern
        clickCount = 0
        totalClicks = 0

        when (pattern.type) {
            PatternType.SINGLE_POINT -> startSinglePointClick(pattern)
            PatternType.MULTI_POINT -> startMultiPointClick(pattern)
            PatternType.INTERVAL_BURST -> startIntervalBurst(pattern)
            PatternType.SHIZUKU_MODE -> {
                // Shizuku mode is handled separately
            }
            PatternType.FOLLOW_TOUCH -> {
                // Follow touch is handled by overlay
            }
        }
    }

    fun stopClicking() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
        currentPattern = null
    }

    private fun startSinglePointClick(pattern: ClickPattern) {
        if (pattern.points.isEmpty()) return

        val point = pattern.points.first()
        scheduleClick(point.x, point.y, pattern.intervalMs)
    }

    private fun startMultiPointClick(pattern: ClickPattern) {
        if (pattern.points.isEmpty()) return

        var currentIndex = 0
        val points = pattern.points

        fun clickNext() {
            if (!isRunning || currentIndex >= points.size) return

            val point = points[currentIndex]
            performClick(point.x, point.y)

            currentIndex = (currentIndex + 1) % points.size

            if (pattern.clickCount == 0 || totalClicks < pattern.clickCount) {
                handler.postDelayed({ clickNext() }, pattern.intervalMs)
            } else {
                stopClicking()
            }
        }

        clickNext()
    }

    private fun startIntervalBurst(pattern: ClickPattern) {
        if (pattern.points.isEmpty()) return

        val point = pattern.points.first()

        fun clickBurst() {
            if (!isRunning) return

            performClick(point.x, point.y)

            if (pattern.clickCount == 0 || totalClicks < pattern.clickCount) {
                handler.postDelayed({ clickBurst() }, pattern.intervalMs)
            } else {
                stopClicking()
            }
        }

        clickBurst()
    }

    private fun scheduleClick(x: Int, y: Int, interval: Long) {
        if (!isRunning) return

        performClick(x, y)

        currentPattern?.let { pattern ->
            if (pattern.clickCount == 0 || totalClicks < pattern.clickCount) {
                handler.postDelayed({
                    scheduleClick(x, y, interval)
                }, interval)
            } else {
                stopClicking()
            }
        }
    }

    private fun performClick(x: Int, y: Int) {
        if (!isRunning) return

        val gestureBuilder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())

        gestureBuilder.addStroke(
            GestureDescription.StrokeDescription(
                path,
                0,
                1
            )
        )

        dispatchGesture(gestureBuilder.build(), null, null)
        totalClicks++
        clickCount++
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not used for now
    }

    override fun onInterrupt() {
        stopClicking()
    }
}
