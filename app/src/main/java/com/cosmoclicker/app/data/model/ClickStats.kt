package com.cosmoclicker.app.data.model

data class ClickStats(
    val totalClicks: Long = 0,
    val sessionClicks: Long = 0,
    val lastClickTime: Long = 0,
    val sessionStartTime: Long = System.currentTimeMillis()
) {
    fun incrementClick(): ClickStats {
        return copy(
            totalClicks = totalClicks + 1,
            sessionClicks = sessionClicks + 1,
            lastClickTime = System.currentTimeMillis()
        )
    }

    fun reset(): ClickStats {
        return copy(
            sessionClicks = 0,
            sessionStartTime = System.currentTimeMillis()
        )
    }
}
