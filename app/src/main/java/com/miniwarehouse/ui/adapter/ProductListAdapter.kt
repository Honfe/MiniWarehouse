package com.miniwarehouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Thing

class ProductListAdapter(val productList : ArrayList<Thing>)
    : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

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
    ): ProductListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_two, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.number.text = product.number.toString()
        holder.storage.text = product.storage.name
        holder.details.text = product.detail

        holder.storageTitle.text = "存储位置"
        holder.detailsTitle.text = "产品详情"
    }

    override fun getItemCount(): Int = productList.size

}