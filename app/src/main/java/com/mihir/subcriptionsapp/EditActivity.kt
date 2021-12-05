package com.mihir.subcriptionsapp;

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.EditActivityBinding
import kotlinx.android.synthetic.main.edit_activity.*
import java.text.SimpleDateFormat
import java.util.*

public class EditActivity : AppCompatActivity() {

    private lateinit var mSubsViewModel: SubsViewModel
    private lateinit var alarmManager:AlarmManager
    private lateinit var pendingIntent:PendingIntent
    var cal = Calendar.getInstance()
    private lateinit var binding: EditActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        binding.dateText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@EditActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })


        mSubsViewModel = ViewModelProvider(this).get(SubsViewModel::class.java)
        binding.updateSubscriptionNameEt.setText(intent.getStringExtra("name"))
        binding.updateDescription.setText(intent.getStringExtra("desc"))
        binding.updateAmt.setText(intent.getStringExtra("amt"))
        binding.dateText.setText(intent.getStringExtra("day"))
        val id = intent.getIntExtra("id",0)
        val requestCode = intent.getIntExtra("requestCode", 11)


        binding.updateBtn.setOnClickListener{
            updateDateInView()
            val name = binding.updateSubscriptionNameEt.text.toString()
            val amt = binding.updateAmt.text.toString()
            val desc = binding.updateDescription.text.toString()
            val day = binding.dateText.text.toString()
            mSubsViewModel.updateSubs(Subscription(id,name,desc,amt,day, requestCode))
            updateReminder(requestCode, name, amt, day)
            finish()
        }
        binding.delete.setOnClickListener(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Subscription")
            builder.setMessage("Are you sure you want to delete the subscription?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Delete"){dialogInterface, which ->
                val name = binding.updateSubscriptionNameEt.text.toString()
                val amt = binding.updateAmt.text.toString()
                val desc = binding.updateDescription.text.toString()
                val day = binding.dateText.text.toString()
                deleteReminder(requestCode)
                Toast.makeText(this,"deleted", Toast.LENGTH_LONG).show()
                mSubsViewModel.deleteSubs(Subscription(id, name, desc, amt, day, requestCode))
            }
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(true)
            alertDialog.show()
        }
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

    private fun updateReminder(requestCode: Int, name: String, amt: String, day: String){
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReciever::class.java)
        intent.putExtra("notification_id", 111)
        intent.putExtra("name", name)
        intent.putExtra("amt", amt)
        intent.putExtra("day", day)

        pendingIntent = PendingIntent.getBroadcast(this,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000,pendingIntent)
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateText.setText(sdf.format(cal.getTime()))
    }

}