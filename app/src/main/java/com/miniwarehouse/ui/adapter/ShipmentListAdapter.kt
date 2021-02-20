package com.miniwarehouse.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.ShipmentInfo

class ShipmentListAdapter(val shipmentList : List<ShipmentInfo>)
    : RecyclerView.Adapter<ShipmentListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) :RecyclerView.ViewHolder(view) {
        val receiver = view.findViewById<View>(R.id.line1_item1) as TextView
        val name = view.findViewById<View>(R.id.line1_item2) as TextView
        val date = view.findViewById<View>(R.id.line2_item2) as TextView
        val details = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShipmentListAdapter.ViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.list_item_two, parent, false)
        return ViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ShipmentListAdapter.ViewHolder, position: Int) {
        val shipment = shipmentList[position]
        holder.receiver.text = shipment.receiver
        holder.name.text = shipment.name
        holder.date.text = shipment.date
        holder.details.text = shipment.detail

        Log.d("hhhh", shipment.date)

        holder.storageTitle.text = "出货日期"
        holder.detailsTitle.text = "出货详情"
    }

    override fun getItemCount(): Int = shipmentList.size

}