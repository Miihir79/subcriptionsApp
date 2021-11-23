package com.mihir.subcriptionsapp;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mihir.subcriptionsapp.databinding.EditActivityBinding

public class EditActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                var binding = EditActivityBinding.inflate(layoutInflater)
                setContentView(binding.root)
        supportActionBar?.hide()
        }
}
