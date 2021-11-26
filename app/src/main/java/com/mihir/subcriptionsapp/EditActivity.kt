package com.mihir.subcriptionsapp;

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.EditActivityBinding

public class EditActivity : AppCompatActivity() {

    private lateinit var mSubsViewModel: SubsViewModel
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

        binding.updateBtn.setOnClickListener{
            val name = binding.updateSubscriptionNameEt.text.toString()
            val amt = binding.updateAmt.text.toString()
            val desc = binding.updateDescription.text.toString()
            val day = binding.updateDate.text.toString()
            mSubsViewModel.updateSubs(Subscription(id,name,desc,amt,day))
            finish()
        }
    }

}