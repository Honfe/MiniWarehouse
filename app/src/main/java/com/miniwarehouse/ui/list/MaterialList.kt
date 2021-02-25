package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.logic.repository.MaterialRepository
import com.miniwarehouse.ui.adapter.MaterialListAdapter
import kotlinx.android.synthetic.main.activity_material_list.*

class MaterialList : AppCompatActivity() {

    private val repository = MaterialRepository(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_list)
        cacheData()
        initRecyclerView()
    }

    private fun cacheData() {
        repository.prepareData()
    }

    private fun initRecyclerView() {
        val arrayList = ArrayList<Material>(repository.getDataList())
        val layoutManager = LinearLayoutManager(this)
        materialListView.layoutManager = layoutManager
        val adapter = MaterialListAdapter(this, arrayList)
        materialListView.adapter = adapter
        updateView(arrayList.size)
    }

    fun updateView(listSize : Int) {
        val listLayout = findViewById<View>(R.id.materialSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.materialNothing) as LinearLayout
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