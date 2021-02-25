package com.miniwarehouse.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.ui.list.AssemblyList
import com.miniwarehouse.ui.list.PackedList
import com.miniwarehouse.ui.widget.DialogHouse

class PackedListAdapter(val context: Context, val packedList : ArrayList<Product>)
    : RecyclerView.Adapter<PackedListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<View>(R.id.line1_item1) as TextView
        val number = view.findViewById<View>(R.id.line1_item2) as TextView
        val storage = view.findViewById<View>(R.id.line2_item2) as TextView
        val details = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView

        val deleteItemBtn = view.findViewById<View>(R.id.deleteItemBtn) as Button
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PackedListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_two, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackedListAdapter.ViewHolder, position: Int) {
        val packed = packedList[position]
        holder.name.text = packed.name
        holder.number.text = packed.number.toString()
        holder.storage.text = packed.storage.name
        holder.details.text = packed.detail

        holder.storageTitle.text = "存储位置"
        holder.detailsTitle.text = "货物详情"

        holder.deleteItemBtn.setOnClickListener {
            val dialog = DialogHouse.getConfirmDialog(context) {
                if (packedList[position].deleteItself() > 0) {
                    packedList.removeAt(position)
                    this.notifyDataSetChanged()
                    (context as PackedList).updateView(itemCount)
                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context, "删除失败!", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }

    override fun getItemCount(): Int = packedList.size

}