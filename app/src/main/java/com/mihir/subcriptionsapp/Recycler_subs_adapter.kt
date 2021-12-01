package com.mihir.subcriptionsapp

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.mihir.subcriptionsapp.data.SubsViewModel
import com.mihir.subcriptionsapp.data.Subscription
import kotlinx.android.synthetic.main.item_subscriptions.view.*

class Recycler_subs_adapter(
    private var subs: List<Subscription>,
    private var ViewModel: SubsViewModel,
    val mctx: Context,
):RecyclerView.Adapter<Recycler_subs_adapter.ViewHolder>() {
    private lateinit var alarmManager:AlarmManager
    private lateinit var pendingIntent:PendingIntent

    inner class ViewHolder(item: View):RecyclerView.ViewHolder(item){
        val name : TextView = item.txt_subName
        val desc : TextView = item.txt_subDesc
        val amt : TextView = item.txt_subAmt
        val day : TextView = item.txt_subDays
        val delete : ImageButton = item.imageButton
        val edit : ImageButton = item.edit


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Recycler_subs_adapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_subscriptions,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: Recycler_subs_adapter.ViewHolder, position: Int) {
        val currentItem = subs[position]
        holder.name.text =subs[position].Name
        holder.desc.text =subs[position].Description
        holder.amt.text =subs[position].Amount
        holder.day.text =subs[position].Interval


        holder.edit.setOnClickListener {
            val intent = Intent(mctx, EditActivity::class.java)
            intent.putExtra("name", holder.name.text)
            intent.putExtra("amt", holder.amt.text)
            intent.putExtra("day", holder.day.text)
            intent.putExtra("desc", holder.desc.text)
            intent.putExtra("id", subs[position].Id)
            intent.putExtra("requestCode", subs[position].RequestCode)
            mctx.startActivity(intent)
            ViewModel.updateSubs(subs[position])
        }

        holder.delete.setOnClickListener {

            val builder = AlertDialog.Builder(mctx)
            builder.setTitle("Delete Subscription")
            builder.setMessage("Are you sure you want to delete the subscription?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)


            builder.setPositiveButton("Delete"){dialogInterface, which ->
                ViewModel.deleteSubs(subs[position])
                val requestCode =subs[position].RequestCode
                deleteReminder(requestCode)
            }
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(true)
            alertDialog.show()

        }
    }

    fun deleteReminder(requestCode: Int){
        alarmManager = mctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mctx,AlarmReciever::class.java)
        intent.putExtra("notification_id", 100);
        intent.putExtra("requestCode", requestCode)
        pendingIntent = PendingIntent.getBroadcast(mctx,requestCode,intent,0)
        // request code has to be same to get it deleted

        alarmManager.cancel(pendingIntent)
    }

    override fun getItemCount(): Int {
        return subs.size
    }
}