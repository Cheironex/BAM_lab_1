package com.example.lab_1

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lab_1.UserData.User
import com.example.lab_1.UserData.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

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
        setContentView(R.layout.user_activity)

        this.userName = intent.getStringExtra(USER_NAME)
        findViewById<TextView>(R.id.textView).apply {
            text = userName
        }

        val filter =
            IntentFilter().apply {
                addAction("NumberAction")
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

    fun onDatabaseClick(view: View) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val db = Room.databaseBuilder(
                    applicationContext,
                    UserDatabase::class.java, "UserDatabase"
                ).build()

                val userData = db.userDao().getAll()
                userData.forEach { user ->
                    println("User Name: ${user.name} ||| User Number : ${user.number}")
                }
            } catch (e: Exception) {
                print("Error: $e !!!!")
            }
        }
    }
}