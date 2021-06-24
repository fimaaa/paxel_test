package com.example.paxeltest.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

object Employee {

    @Entity(tableName = "employee_table")
    @Parcelize
    data class Data(
        @PrimaryKey
        val id: String,
        val employee_name: String,
        val employee_salary: String,
        val employee_age: String,
        val profile_image: String
    ) : Parcelable
}