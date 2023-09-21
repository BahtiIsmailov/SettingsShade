package com.example.customstatusbar.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.customstatusbar.service.CustomAccessibilityService
import com.example.customstatusbar.service.CustomBoundService


class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, CustomBoundService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}
