package com.example.lab_1

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.example.lab_1.UserData.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LabContentProvider : ContentProvider() {
//    companion object {
//        const val PROVIDER_NAME = "com.demo.user.provider"
//        const val URL = "content://$PROVIDER_NAME/users"
//
//    }


    private lateinit var userDatabase: UserDatabase

    override fun onCreate(): Boolean {
        try {
            userDatabase = Room.databaseBuilder(context!!, UserDatabase::class.java, "UserDatabase").build()
        } catch (e: Exception) {
            print("Error: $e !!!!")
            return false
        }
            return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
       return userDatabase.query(SupportSQLiteQueryBuilder.builder("user").selection(selection, selectionArgs).columns(projection).orderBy(sortOrder).create())
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val retId =  userDatabase.openHelper.writableDatabase.insert("user", 0, values)
        return ContentUris.withAppendedId(uri, retId)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return userDatabase.openHelper.writableDatabase.delete("user", selection, selectionArgs)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return userDatabase.openHelper.writableDatabase.update("user",0,values, selection, selectionArgs)
    }

}