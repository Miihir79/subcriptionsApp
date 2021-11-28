package com.mihir.subcriptionsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Subscription::class],version = 1,exportSchema = false)
abstract class SubsDatabase: RoomDatabase() {

    abstract fun subsDao(): Subs_dao

    companion object{
        @Volatile
        private var INSTANCE: SubsDatabase?=null

        fun getDatabase(context: Context): SubsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!= null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubsDatabase::class.java,
                    "subscriptions_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}