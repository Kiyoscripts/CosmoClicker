package com.cosmoclicker.app.util

import android.content.Context
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper
import android.content.IInputManager
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ShizukuHolder {
    private val _isAvailable = MutableStateFlow(false)
    val isAvailable: StateFlow<Boolean> = _isAvailable.asStateFlow()

    private var inputManager: IInputManager? = null

    fun init(context: Context) {
        Shizuku.addBinderReceivedListener {
            _isAvailable.value = true
            inputManager = IInputManager.Stub.asInterface(
                ShizukuBinderWrapper(SystemServiceHelper.getSystemService("input"))
            )
        }

        Shizuku.addBinderDeadListener {
            _isAvailable.value = false
            inputManager = null
        }

        if (Shizuku.isPreV11()) {
            _isAvailable.value = false
        } else {
            if (Shizuku.isBinderAlive()) {
                _isAvailable.value = true
                try {
                    inputManager = IInputManager.Stub.asInterface(
                        ShizukuBinderWrapper(SystemServiceHelper.getSystemService("input"))
                    )
                } catch (e: Exception) {
                    _isAvailable.value = false
                }
            }
        }
    }

    fun isShizukuAvailable(): Boolean {
        return Shizuku.isPreV11() || (Shizuku.isBinderAlive() && Shizuku.checkSelfPermission() == 0)
    }

    fun getInputManager(): IInputManager? {
        return inputManager
    }

    fun requestPermission(context: Context) {
        if (!Shizuku.isPreV11() && Shizuku.isBinderAlive()) {
            if (Shizuku.checkSelfPermission() != 0) {
                Shizuku.requestPermission(1001)
            }
        }
    }
}
