package com.gksoftwaresolutions.catapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import io.reactivex.Flowable

@Dao
interface VoteDao {
    @Query("SELECT * FROM vote_table")
    fun selectAll(): Flowable<List<Vote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Vote)

}