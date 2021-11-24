package com.mihir.subcriptionsapp;

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navArgs
import com.mihir.subcriptionsapp.data.Subscription
import com.mihir.subcriptionsapp.databinding.EditActivityBinding

public class EditActivity : AppCompatActivity() {
        val ourData: LiveData<Subscription>
                get() = ourData

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                var binding = EditActivityBinding.inflate(layoutInflater)
                setContentView(binding.root)
        supportActionBar?.hide()


                binding.updateSubscriptionNameEt.setText(intent.getStringExtra("name"))
                binding.updateDescription.setText(intent.getStringExtra("desc"))
                binding.updateAmt.setText(intent.getStringExtra("amt"))
                binding.updateDate.setText(intent.getStringExtra("day"))
        }
}
