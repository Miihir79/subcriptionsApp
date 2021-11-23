package com.mihir.subcriptionsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mihir.subcriptionsapp.data.Subscription
import kotlinx.android.synthetic.main.item_subscriptions.view.*

class Recycler_subs_adapter(private var subs:List<Subscription>):RecyclerView.Adapter<Recycler_subs_adapter.ViewHolder>() {

    inner class ViewHolder(item: View):RecyclerView.ViewHolder(item){
        val name : TextView = item.txt_subName
        val desc : TextView = item.txt_subDesc
        val amt : TextView = item.txt_subAmt
        val day : TextView = item.txt_subDays

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
    }

    override fun getItemCount(): Int {
        return subs.size
    }
}