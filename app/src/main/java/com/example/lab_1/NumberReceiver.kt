package com.example.lab_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        val userName = p1.getStringExtra("USER")
        val number = p1.getStringExtra("NUMBER")

        println(" User: $userName number: $number")
    }

}