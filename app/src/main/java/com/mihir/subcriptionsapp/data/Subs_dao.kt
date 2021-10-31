package com.mihir.subcriptionsapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Subs_dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubs(subs: Subscription)

    @Query("SELECT * from subscriptions_table")
    fun readAllSubs(): LiveData<List<Subscription>>

    @Update
    suspend fun updateSubs(subs: Subscription)

    @Delete
    suspend fun deleteSubs(subs: Subscription)
}