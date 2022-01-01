package com.example.lab_1

import android.content.*
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    private var mBound: Boolean = false
    private lateinit var mService: LoginService
    private val numberReceiver: BroadcastReceiver = NumberReceiver()
    private var userName: String? = String()

    private val connection = object : ServiceConnection {


        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LoginService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        this.userName = intent.getStringExtra(USER_NAME)
        findViewById<TextView>(R.id.textView).apply {
            text = userName
        }

        val filter =
            IntentFilter(ConnectivityManager.ACTION_CAPTIVE_PORTAL_SIGN_IN).apply {
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }

        registerReceiver(numberReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(numberReceiver)
        super.onDestroy()
    }

    fun onServiceButtonClick(view: View) {
        Intent(this, LoginService::class.java).also { intent ->
            intent.putExtra(USER_NAME,this.userName)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        if (mBound) {
            mService.startTimer()
        }
    }

    fun onStopClick(view: View) {
        if (mBound) {
            unbindService(connection)
            mBound = false
        } else {
            println("No Services Running")
        }
    }
}