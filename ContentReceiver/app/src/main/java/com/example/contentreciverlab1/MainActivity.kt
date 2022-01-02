package com.example.contentreciverlab1

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var CONTENT_URI = Uri.parse("content://com.demo.user.provider/users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("Range")
    fun onClickShowDetails(view: View?) {
        try {
            // inserting complete table details in this text field
            val resultView = findViewById<View>(R.id.res) as TextView

            // creating a cursor object of the
            // content URI
            val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)

            // iteration of the cursor
            // to print whole table
            if (cursor!!.moveToFirst()) {
                val strBuild = StringBuilder()
                while (!cursor.isAfterLast) {
                    strBuild.append(
                        """
      
    ${cursor.getString(cursor.getColumnIndex("Name"))}-${cursor.getString(cursor.getColumnIndex("Number"))}
    """.trimIndent()
                    )
                    cursor.moveToNext()
                }
                resultView.text = strBuild
            } else {
                resultView.text = "No Records Found"
            }
        }
        catch(e: Exception)
        {
            print("Error: $e !!!!")
        }
    }

}
