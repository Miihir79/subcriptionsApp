package com.mihir.subcriptionsapp;

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.EditActivityBinding
import java.util.*

public class EditActivity : AppCompatActivity() {

    private lateinit var mSubsViewModel: SubsViewModel
    private lateinit var alarmManager:AlarmManager
    private lateinit var pendingIntent:PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mSubsViewModel = ViewModelProvider(this).get(SubsViewModel::class.java)
        binding.updateSubscriptionNameEt.setText(intent.getStringExtra("name"))
        binding.updateDescription.setText(intent.getStringExtra("desc"))
        binding.updateAmt.setText(intent.getStringExtra("amt"))
        binding.updateDate.setText(intent.getStringExtra("day"))
        val id = intent.getIntExtra("id",0)
        val requestCode = intent.getIntExtra("requestCode", 11)


        binding.updateBtn.setOnClickListener{
            val name = binding.updateSubscriptionNameEt.text.toString()
            val amt = binding.updateAmt.text.toString()
            val desc = binding.updateDescription.text.toString()
            val day = binding.updateDate.text.toString()
            mSubsViewModel.updateSubs(Subscription(id,name,desc,amt,day, requestCode))
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
                val day = binding.updateDate.text.toString()
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
}