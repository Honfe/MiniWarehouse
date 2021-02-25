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
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.ui.list.AssemblyList
import com.miniwarehouse.ui.list.MaterialList
import com.miniwarehouse.ui.widget.DialogHouse

class MaterialListAdapter(val context: Context, val materialList : ArrayList<Material>)
    : RecyclerView.Adapter<MaterialListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<View>(R.id.line1_item1) as TextView
        val type: TextView = view.findViewById<View>(R.id.line1_item2) as TextView
        val number: TextView = view.findViewById<View>(R.id.line1_item3) as TextView
        val storage: TextView = view.findViewById<View>(R.id.line2_item2) as TextView
        val details: TextView = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView

        val deleteItemBtn = view.findViewById<View>(R.id.deleteItemBtn) as Button
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

        holder.deleteItemBtn.setOnClickListener {
            val dialog = DialogHouse.getConfirmDialog(context) {
                if (materialList[position].deleteItself() > 0) {
                    materialList.removeAt(position)
                    this.notifyDataSetChanged()
                    (context as MaterialList).updateView(itemCount)
                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context, "删除失败!", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }

    override fun getItemCount(): Int = materialList.size

}