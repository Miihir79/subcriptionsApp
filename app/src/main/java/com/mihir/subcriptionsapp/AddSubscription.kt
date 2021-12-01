package com.mihir.subcriptionsapp

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.ActivityAddSubscriptionBinding
import kotlinx.android.synthetic.main.activity_add_subscription.*
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class AddSubscription : AppCompatActivity() {
    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()

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

        textview_date = this.text_view_date_1
        button_date = this.button_date_1

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@AddSubscription,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        binding.txtFinish.setOnClickListener {
            setReminder()
            Toast.makeText(this,"set",Toast.LENGTH_LONG).show()
            finish()
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
        val date = textview_date?.text.toString()
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

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }

    fun generateRequestCode() : Int{
            return abs(Random().nextInt())
    }
}