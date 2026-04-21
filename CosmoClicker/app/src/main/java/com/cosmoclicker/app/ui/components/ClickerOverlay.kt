package com.cosmoclicker.app.ui.components

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.cosmoclicker.app.R

class ClickerOverlay(private val context: Context) {

    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: View? = null
    private var isShowing = false

    fun show() {
        if (isShowing) return

        val layout = FrameLayout(context).apply {
            setBackgroundColor(context.getColor(android.R.color.transparent))
        }

        val params = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.TOP or Gravity.END
            x = 100
            y = 100
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }
        }

        windowManager.addView(layout, params)
        overlayView = layout
        isShowing = true
    }

    fun hide() {
        if (!isShowing || overlayView == null) return

        try {
            windowManager.removeView(overlayView)
        } catch (e: IllegalArgumentException) {
            // View was not attached
        }

        overlayView = null
        isShowing = false
    }

    fun isShowing(): Boolean = isShowing
}

@Composable
fun rememberClickerOverlay(): ClickerOverlay {
    val context = LocalContext.current
    return remember(context) { ClickerOverlay(context) }
}
