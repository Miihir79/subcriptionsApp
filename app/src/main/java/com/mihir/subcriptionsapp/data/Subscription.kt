package com.mihir.subcriptionsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions_table")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    var Id:Int,
    var Name: String,
    var Description: String,
    var Amount: String,
    var Interval: String,
//    var requestCode:Int

)
