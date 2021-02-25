package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.repository.GoodsRepository
import com.miniwarehouse.ui.adapter.PackedListAdapter
import kotlinx.android.synthetic.main.activity_packed_list.*

class PackedList : AppCompatActivity() {

    private val repository = GoodsRepository(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packed_list)
        cacheData()
        initRecyclerView()
    }

    private fun cacheData() {
        repository.prepareData()
    }

    fun initRecyclerView() {
        val arrayList = ArrayList<Product>(repository.getDataList())
        val layoutManager = LinearLayoutManager(this)
        packedListView.layoutManager = layoutManager
        val adapter = PackedListAdapter(this, arrayList)
        packedListView.adapter = adapter
        updateView(arrayList.size)
    }

    fun updateView(listSize : Int) {
        val listLayout = findViewById<View>(R.id.packedSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.packedNothing) as LinearLayout
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