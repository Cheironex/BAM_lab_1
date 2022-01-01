package com.example.lab_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.example.lab_1.UserData.User
import com.example.lab_1.UserData.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class NumberReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context, p1: Intent) {

        val userName = p1.getStringExtra(USER_NAME)
        val number = p1.getStringExtra("NUMBER")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                val db = Room.databaseBuilder(
                    p0,
                    UserDatabase::class.java, "UserDatabase"
                ).build()
                db.userDao().insert(User(userName, number))
            } catch (e: Exception) {
                print("Error: $e !!!!")
            }
        }
        println(" User: $userName number: $number")

    }

}