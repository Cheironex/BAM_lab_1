package com.example.lab_1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*
import java.util.*


class LoginService : Service() {
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LoginService = this@LoginService
    }
    private var userName: String? = String()

    private var jobList = mutableListOf<Deferred<Int>>()
    private var shouldRun = true

    override fun onBind(intent: Intent): IBinder {
        userName = intent.getStringExtra(USER_NAME)
        shouldRun = true
        return binder
    }
    override fun onCreate() {
        super.onCreate()

        startTimer()

    }



    fun startTimer() = runBlocking{
        CoroutineScope(Dispatchers.IO)
        val job =  async {
            var time = 0

            while(shouldRun) {
                println(this.toString() + " time: " + time++)

                delay(1000)
            }
            return@async time
        }
        jobList.add(job)
    }

    private fun getTime() = runBlocking {
        if(jobList.size>0){
            val number = jobList[0].await()
            Intent().also { intent ->
                intent.putExtra(USER_NAME, userName)
                intent.putExtra("number", number.toString())
//                intent.setAction("android.intent.action.ACTION_AIRPLANE_MODE_CHANGED")
                sendBroadcast(intent)
            }
        }

    }

    override fun onUnbind(intent: Intent?): Boolean {

        shouldRun = false
        getTime()

        return super.onUnbind(intent)
    }
}