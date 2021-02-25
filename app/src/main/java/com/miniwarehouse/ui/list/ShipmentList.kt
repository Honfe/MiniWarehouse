package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.logic.repository.ShipmentRepository
import com.miniwarehouse.ui.adapter.ShipmentListAdapter
import kotlinx.android.synthetic.main.activity_shipment_list.*

class ShipmentList : AppCompatActivity() {

    private val repository = ShipmentRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipment_list)
        cacheData()
        initRecyclerView()
    }

    private fun cacheData() {
        repository.prepareData()
    }

    fun initRecyclerView() {
        val arrayList = ArrayList<ShipmentInfo>(repository.getDataList())
        val layoutManager = LinearLayoutManager(this)
        shipmentListView.layoutManager = layoutManager
        val adapter = ShipmentListAdapter(this, arrayList)
        shipmentListView.adapter = adapter
        updateView(arrayList.size)
    }

    fun updateView(listSize : Int) {
        val listLayout = findViewById<View>(R.id.shipmentSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.shipmentNothing) as LinearLayout
        if (listSize <= 0) {
            listLayout.visibility = LinearLayout.GONE
            emptyLayout.visibility = LinearLayout.VISIBLE
        }
        else {
            listLayout.visibility = LinearLayout.VISIBLE
            emptyLayout.visibility = LinearLayout.GONE
        }
    }

}