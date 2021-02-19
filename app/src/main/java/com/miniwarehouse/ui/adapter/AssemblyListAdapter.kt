package com.miniwarehouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Product

class AssemblyListAdapter(val assemblyList : List<Product>)
    :RecyclerView.Adapter<AssemblyListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<View>(R.id.line1_item1) as TextView
        val number = view.findViewById<View>(R.id.line1_item2) as TextView
        val storage = view.findViewById<View>(R.id.line2_item2) as TextView
        val details = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssemblyListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_two, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssemblyListAdapter.ViewHolder, position: Int) {
        val assembly = assemblyList[position]
        holder.name.text = assembly.name
        holder.number.text = assembly.number.toString()
        holder.storage.text = assembly.storage.name
        holder.details.text = assembly.detail

        holder.storageTitle.text = "存储位置"
        holder.detailsTitle.text = "配件详情"
    }

    override fun getItemCount(): Int = assemblyList.size

}