package com.example.paxeltest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paxeltest.data.model.Example
import com.example.paxeltest.data.source.dao.ExampleDao

@Database(
    entities = [Example.Data::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getFavoriteMovieDao(): ExampleDao
}