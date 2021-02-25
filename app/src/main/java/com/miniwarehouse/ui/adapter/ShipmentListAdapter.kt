package com.miniwarehouse.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.miniwarehose.R
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.ui.list.AssemblyList
import com.miniwarehouse.ui.list.ShipmentList
import com.miniwarehouse.ui.widget.DialogHouse

class ShipmentListAdapter(val context: Context, val shipmentList : ArrayList<ShipmentInfo>)
    : RecyclerView.Adapter<ShipmentListAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) :RecyclerView.ViewHolder(view) {
        val receiver = view.findViewById<View>(R.id.line1_item1) as TextView
        val name = view.findViewById<View>(R.id.line1_item2) as TextView
        val date = view.findViewById<View>(R.id.line2_item2) as TextView
        val details = view.findViewById<View>(R.id.line3_item2) as TextView

        val storageTitle = view.findViewById<View>(R.id.line2_item1) as TextView
        val detailsTitle = view.findViewById<View>(R.id.line3_item1) as TextView

        val deleteItemBtn = view.findViewById<View>(R.id.deleteItemBtn) as Button
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShipmentListAdapter.ViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.list_item_two, parent, false)
        return ViewHolder(holder)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShipmentListAdapter.ViewHolder, position: Int) {
        val shipment = shipmentList[position]
        holder.receiver.text = shipment.receiver
        holder.name.text = shipment.name
        holder.date.text = shipment.date
        holder.details.text = shipment.detail

        val pre : String = if (shipment.status == 0) "出货" else "退货"
        holder.storageTitle.text = pre + "日期"
        holder.detailsTitle.text = pre + "详情"

        holder.deleteItemBtn.setOnClickListener {
            val dialog = DialogHouse.getConfirmDialog(context) {
                if (shipmentList[position].deleteItself() > 0) {
                    shipmentList.removeAt(position)
                    this.notifyDataSetChanged()
                    (context as ShipmentList).updateView(itemCount)
                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context, "删除失败!", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }

    override fun getItemCount(): Int = shipmentList.size

}