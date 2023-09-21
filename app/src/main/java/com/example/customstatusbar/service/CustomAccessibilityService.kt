package com.example.customstatusbar.service

//noinspection SuspiciousImport

import android.R
import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.view.KeyEvent
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.customstatusbar.view.ContentView


class CustomBoundService : Service() {

    class LocalBinder : Binder() {
        val service: CustomBoundService
            get() = CustomBoundService()
    }

    private val mBinder = LocalBinder()
    override fun onBind(intent: Intent?): IBinder {
        val serviceIntent = Intent(this, CustomAccessibilityService::class.java)
        this.startService(serviceIntent)
        return mBinder;
    }
}

class CustomAccessibilityService : AccessibilityService(), SavedStateRegistryOwner, LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    private val windowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    private lateinit var contentView: ComposeView

    private var isViewAttached = false

    private val savedStateRegistryController by lazy {
        SavedStateRegistryController.create(this)
    }
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        val notification = NotificationCompat.Builder(this, "123")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentTitle("CustomAccessibilityService")
            .setContentText("Service is running in foreground...")
            .build()
        startForeground(123, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        return super.onStartCommand(intent, flags, startId)
    }

    private fun destroyView() {
        if (isViewAttached) {
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            contentView.animate()
                .translationY(-screenHeight.toFloat())
                .setDuration(1000)
                .withEndAction {
                    isViewAttached = false
                    windowManager.removeView(contentView)
                }.start()
        }
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val action: Int = event.action
        val keyCode: Int = event.keyCode
        if (action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                initView(false)
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                initView(true)
            }
        }
        return super.onKeyEvent(event)
    }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
         // ignore
    }

    private fun initView(createScreen: Boolean) {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        if (createScreen && !isViewAttached) {
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
            )
            contentView = ComposeView(this).apply {
                setViewTreeSavedStateRegistryOwner(this@CustomAccessibilityService)
                setViewTreeLifecycleOwner(this@CustomAccessibilityService)
                setContent {
                    ContentView()
                }
            }
            contentView.translationY = -screenHeight.toFloat()
            windowManager.addView(contentView, params)
            contentView.animate().translationY(0f).setDuration(1000).start()
            isViewAttached = true
        }else{
            destroyView()
        }
    }

    override fun onInterrupt() {
        if (isViewAttached) {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
            windowManager.removeView(contentView)
            isViewAttached = false
        }
    }
}