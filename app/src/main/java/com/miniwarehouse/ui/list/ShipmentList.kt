package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.ui.adapter.ShipmentListAdapter
import kotlinx.android.synthetic.main.activity_shipment_list.*

class ShipmentList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipment_list)
        initRecyclerView()
    }

    fun initRecyclerView() {
        val arrayList = arrayListOf<ShipmentInfo>()
        val listLayout = findViewById<View>(R.id.shipmentSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.shipmentNothing) as LinearLayout
        if (arrayList.size <= 0) {
            listLayout.visibility = LinearLayout.GONE
            emptyLayout.visibility = LinearLayout.VISIBLE
        }
        else {
            val layoutManager = LinearLayoutManager(this)
            shipmentListView.layoutManager = layoutManager
            val adapter = ShipmentListAdapter(arrayList)
            shipmentListView.adapter = adapter
            listLayout.visibility = LinearLayout.VISIBLE
            listLayout.visibility = LinearLayout.GONE
        }
    }

}