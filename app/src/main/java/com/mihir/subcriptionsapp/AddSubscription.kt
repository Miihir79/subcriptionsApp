package com.mihir.subcriptionsapp

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.ActivityAddSubscriptionBinding
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Math.abs
import java.util.*

class AddSubscription : AppCompatActivity() {

    private lateinit var binding: ActivityAddSubscriptionBinding
    private lateinit var alarmManager:AlarmManager
    private lateinit var pendingIntent:PendingIntent
    private lateinit var mSubsViewModel: SubsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        createNotificationChannel()

        mSubsViewModel =ViewModelProvider(this).get(SubsViewModel::class.java)

        binding.txtFinish.setOnClickListener {
            setReminder()
            Toast.makeText(this,"set",Toast.LENGTH_LONG).show()
            finish()
        }

        binding.txtDelete.setOnClickListener{
            deleteReminder(11)
            Toast.makeText(this,"deleted",Toast.LENGTH_LONG).show()
        }
    }

    fun createNotificationChannel(){
        // we do not need a notif channel for versions below O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "encodeSubscriptionsAppChannel"
            val description = "Notification channel for the subscriptions reminder app"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("subscriptionsApp",name,importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }

    }

    fun setReminder(){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReciever::class.java)
        val name = binding.edTxtSubName.text.toString()
        val date = binding.editTextDate.text.toString()
        val amt = binding.edTxtAmount.text.toString()
        val desc = binding.edTxtSubDesc.text.toString()
        val requestCode:Int = generateRequestCode()
        intent.putExtra("name",name)
        intent.putExtra("date",date)
        intent.putExtra("amt",amt)
        intent.putExtra("requestCode",requestCode)

        mSubsViewModel.addSubs(Subscription(0,name,desc,amt,date,requestCode))

        pendingIntent = PendingIntent.getBroadcast(this,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000,pendingIntent)
    }

    fun deleteReminder(requestCode: Int){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReciever::class.java)
        intent.putExtra("notification_id", 100);

        pendingIntent = PendingIntent.getBroadcast(this,requestCode,intent,0)
        // request code has to be same to get it deleted

        alarmManager.cancel(pendingIntent)

        finish()
    }

    fun generateRequestCode() : Int{
            return abs(Random().nextInt())
    }
}