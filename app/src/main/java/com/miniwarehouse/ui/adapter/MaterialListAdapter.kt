package com.miniwarehouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Material

class MaterialListAdapter(val materialList : List<Material>)
    : RecyclerView.Adapter<MaterialListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<View>(R.id.line1_item1) as TextView
        val type: TextView = view.findViewById<View>(R.id.line1_item2) as TextView
        val number: TextView = view.findViewById<View>(R.id.line1_item3) as TextView
        val storage: TextView = view.findViewById<View>(R.id.line2_item2) as TextView
        val details: TextView = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MaterialListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_three, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaterialListAdapter.ViewHolder, position: Int) {
        val material = materialList[position]
        holder.name.text = material.name
        holder.type.text = material.type
        holder.number.text = material.number.toString()
        holder.storage.text = material.storage.name
        holder.details.text = material.detail

        holder.storageTitle.text = "存储位置"
        holder.detailsTitle.text = "原料详情"
    }

    override fun getItemCount(): Int = materialList.size

}