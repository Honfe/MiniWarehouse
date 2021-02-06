package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.ui.adapter.PackedListAdapter
import kotlinx.android.synthetic.main.activity_packed_list.*

class PackedList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packed_list)
        initRecyclerView()
    }

    fun initRecyclerView() {
        val arrayList = arrayListOf<Thing>()
        val listLayout = findViewById<View>(R.id.packedSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.packedNothing) as LinearLayout
        if (arrayList.size <= 0) {
            listLayout.visibility = LinearLayout.GONE
            emptyLayout.visibility = LinearLayout.VISIBLE
        }
        else {
            val layoutManager = LinearLayoutManager(this)
            packedListView.layoutManager = layoutManager
            val adapter = PackedListAdapter(arrayList)
            packedListView.adapter = adapter
            listLayout.visibility = LinearLayout.VISIBLE
            emptyLayout.visibility = LinearLayout.GONE
        }
    }
}