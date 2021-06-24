package com.example.paxeltest.data.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paxeltest.data.model.Example

@Dao
interface ExampleDao {
    @Insert
    suspend fun addExample(example: Example.Data)

    @Query("SELECT * FROM example_table")
    fun getAllExample(): LiveData<MutableList<Example.Data>>

    @Query("SELECT count(*) FROM example_table WHERE example_table.id = :id")
    suspend fun checkExample(id: String): Int

    @Query("DELETE FROM example_table WHERE example_table.id = :id" )
    suspend fun removeExample(id: String) : Int
}