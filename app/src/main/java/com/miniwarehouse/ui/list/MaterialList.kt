package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.ui.adapter.MaterialListAdapter
import kotlinx.android.synthetic.main.activity_material_list.*

class MaterialList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val arrayList = arrayListOf<Material>()
        val listLayout = findViewById<View>(R.id.materialSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.materialNothing) as LinearLayout
        if (arrayList.size <= 0) {
            listLayout.visibility = LinearLayout.GONE
            emptyLayout.visibility = LinearLayout.VISIBLE
        }
        else {
            val layoutManager = LinearLayoutManager(this)
            materialListView.layoutManager = layoutManager
            val adapter = MaterialListAdapter(arrayList)
            materialListView.adapter = adapter
            listLayout.visibility = LinearLayout.VISIBLE
            emptyLayout.visibility = LinearLayout.GONE
        }
    }

}