package com.example.lab_1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*
import kotlin.concurrent.timer
import android.os.Binder
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.produce


class LoginService : Service() {
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LoginService = this@LoginService
    }
    private var userName: String? = String()

    private var JobList = mutableListOf<Deferred<Int>>()
    var shouldRun = true
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
        val Scope = CoroutineScope(Dispatchers.IO)
        val job =  async {
            var time = 0;

            while(shouldRun) {
                println(this.toString() + " time: " + time++)

                delay(1000)
            }
            return@async time
        }
        JobList.add(job)
    }

    fun getTime() = runBlocking {
        if(JobList.size>0){
            val number = JobList[0].await()
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