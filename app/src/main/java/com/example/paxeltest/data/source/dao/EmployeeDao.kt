package com.example.paxeltest.data.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paxeltest.data.model.Employee

@Dao
interface EmployeeDao {
    @Insert
    suspend fun addExample(example: Employee.Data)

    @Query("SELECT * FROM employee_table")
    fun getAllExample(): LiveData<MutableList<Employee.Data>>

    @Query("SELECT count(*) FROM employee_table WHERE employee_table.id = :id")
    suspend fun checkExample(id: String): Int

    @Query("DELETE FROM employee_table WHERE employee_table.id = :id")
    suspend fun removeExample(id: String): Int
}