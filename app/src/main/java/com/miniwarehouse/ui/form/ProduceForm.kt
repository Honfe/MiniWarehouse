package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.miniwarehose.R
import com.miniwarehouse.ui.adapter.CommonPagerAdapter

class ProduceForm : AppCompatActivity() {

    private lateinit var vpager : ViewPager
    private lateinit var pagerList : ArrayList<View>
    private lateinit var adapter : PagerAdapter

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce_form)

        vpager = findViewById(R.id.producePager)
        val lyinfater : LayoutInflater = layoutInflater
        pagerList = arrayListOf(
                lyinfater.inflate(R.layout.pager_item_produce_assembly, null, false),
                lyinfater.inflate(R.layout.pager_item_produce_product, null, false)
        )
        this.adapter = CommonPagerAdapter(pagerList)
        vpager.adapter = this.adapter
    }
}