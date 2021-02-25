package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.repository.AssemblyRepository
import com.miniwarehouse.ui.adapter.AssemblyListAdapter
import kotlinx.android.synthetic.main.activity_assembly_list.*

class AssemblyList : AppCompatActivity() {

    private val repository = AssemblyRepository(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly_list)
        cacheData()
        initRecyclerView()
    }

    private fun cacheData() {
        repository.prepareData()
    }

    fun initRecyclerView() {
        val arrayList = ArrayList<Product>(repository.getDataList())
        val layoutManager = LinearLayoutManager(this)
        assemblyListView.layoutManager = layoutManager
        val adapter = AssemblyListAdapter(this, arrayList)
        assemblyListView.adapter = adapter
        updateView(arrayList.size)
    }

    fun updateView(listSize : Int) {
        val listLayout = findViewById<View>(R.id.assemblySomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.assemblyNothing) as LinearLayout
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