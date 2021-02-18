package com.miniwarehouse.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.miniwarehose.R
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.ui.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        initRecyclerView()
    }

    fun initRecyclerView() {
        val arrayList = arrayListOf<Product>()
        val listLayout = findViewById<View>(R.id.productSomething) as LinearLayout
        val emptyLayout = findViewById<View>(R.id.productNothing) as LinearLayout
        if (arrayList.size <= 0) {
            listLayout.visibility = LinearLayout.GONE
            emptyLayout.visibility = LinearLayout.VISIBLE
        }
        else {
            val layoutManager = LinearLayoutManager(this)
            productListView.layoutManager = layoutManager
            val adapter = ProductListAdapter(arrayList)
            productListView.adapter = adapter
            listLayout.visibility = LinearLayout.VISIBLE
            emptyLayout.visibility = LinearLayout.GONE
        }
    }

}