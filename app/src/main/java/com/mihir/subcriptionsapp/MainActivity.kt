package com.mihir.subcriptionsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_subscriptions.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mSubsViewModel: SubsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mSubsViewModel = ViewModelProvider(this).get(SubsViewModel::class.java)

        binding.FabAddSub.setOnClickListener{
            val intent = Intent(this,AddSubscription::class.java)
            startActivity(intent)
        }

//        binding.rvSubs.edit.setOnClickListener{
//            val intent2 = Intent(this, EditActivity::class.java)
//            startActivity(intent2)
//        }
        mSubsViewModel.readAllData.observe(this,{Subs->
            binding.rvSubs.layoutManager = LinearLayoutManager(this)
            binding.rvSubs.adapter = Recycler_subs_adapter(Subs)


        })

    }

}