package com.example.lab_1.UserData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(

    @ColumnInfo(name = "Name") val name: String?,
    @ColumnInfo(name = "Number") val number: String?
)
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
