package com.gksoftwaresolutions.catapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gksoftwaresolutions.catapp.data.local.dao.VoteDao
import com.gksoftwaresolutions.catapp.data.local.entity.Vote

@Database(entities = [Vote::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun voteDao(): VoteDao

    // Singleton prevents multiple instances of database opening at the
    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "cat_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}