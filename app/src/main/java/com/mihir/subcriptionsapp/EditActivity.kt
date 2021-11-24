package com.mihir.subcriptionsapp;

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.navArgs
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.EditActivityBinding

public class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.updateSubscriptionNameEt.setText(intent.getStringExtra("name"))
        binding.updateDescription.setText(intent.getStringExtra("desc"))
        binding.updateAmt.setText(intent.getStringExtra("amt"))
        binding.updateDate.setText(intent.getStringExtra("day"))

        binding.updateBtn.setOnClickListener(){
//            val name = binding.updateSubscriptionNameEt.text.toString()
//            val amt = binding.updateAmt.text.toString()
//            val desc = binding.updateDescription.text.toString()
//            val day = binding.updateDate.text.toString()
            val myIntent = Intent(this, MainActivity::class.java)
//            myIntent.putExtra("name", name)
//            myIntent.putExtra("amt", amt)
//            myIntent.putExtra("desc", desc)
//            myIntent.putExtra("day", day)
            startActivity(myIntent)
        }
    }
}