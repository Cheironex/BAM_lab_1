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
        fun getService(): LoginService = this@LoginService
    }
    private var userName: String? = String()

    private var jobList = mutableListOf<Job>()
    private var numbers = mutableListOf<Int>()
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
        numbers.add(0);
        var numberId = numbers.size - 1
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            val id =numberId
            while(shouldRun) {
                println(this.toString() + " time: " + numbers[id]++)

                delay(1000)
            }
        }
        jobList.add(job)
    }

    private fun getTime() = runBlocking {

            val number = numbers[0]
        println("Starting Broadcast with number = $number")
            Intent().also { intent ->
                intent.putExtra(USER_NAME, userName)
                intent.putExtra("number", number.toString())
                sendBroadcast(intent)
            }


    }

    override fun onUnbind(intent: Intent?): Boolean {

        shouldRun = false
        getTime()

        return super.onUnbind(intent)
    }
}