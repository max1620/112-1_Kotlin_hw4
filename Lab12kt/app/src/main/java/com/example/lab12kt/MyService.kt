package com.example.lab12kt

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {

    val serviceJob = Job()
    val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    override fun onCreate() {
        super.onCreate()

        /*Thread {
            try {
                Thread.sleep(3000)
                val intent = Intent(this, SecActivity::class.java)  //intent從service啟動SecActivity
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }*/

        serviceScope.launch {
            delay(3000)
            startActivity()
        }.start()
    }
    override fun onStartCommand(intent: Intent, flags: Int, startid: Int): Int {
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? = null

    private fun startActivity() {
        val intent = Intent(this, SecActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel() // 在服務銷毀時取消所有的協程
    }
}