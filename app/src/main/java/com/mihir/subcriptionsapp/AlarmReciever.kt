package com.mihir.subcriptionsapp

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.SharedPreferences

class AlarmReciever: BroadcastReceiver() { // also, we have to add it to the manifest file

    override fun onReceive(p0: Context?, p1: Intent?) {
        val mPrefs: SharedPreferences = p0!!.getSharedPreferences("appsettings", 0)
        val i = Intent(p0,MainActivity::class.java)
        p1!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val name = p1.getStringExtra("name")
        val date = p1.getStringExtra("date")
        val amount = p1.getStringExtra("amt")
        val requestCode = p1.getIntExtra("requestCode", -1)
        val pendingIntent = PendingIntent.getActivity(p0,requestCode,i,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(p0,"subscriptionsApp")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Pay your bills")
            .setContentText("Pay your $name bill of $amount due on $date")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        // check vibration.
        if (mPrefs.getBoolean("vibration", true)) {
            builder.setVibrate(longArrayOf(0, 50))
        }

        val notificationManager = NotificationManagerCompat.from(p0)
        notificationManager.notify(124,builder.build())

        val id = p1.getIntExtra("notification_id", -1)
        if(id!=-1){
            if(id==100)
                notificationManager.cancel(id)
        }

    }
}