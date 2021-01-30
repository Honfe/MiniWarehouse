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

class PurchaseForm : AppCompatActivity() {

    private lateinit var vpager : ViewPager
    private lateinit var pagerList : ArrayList<View>
    private lateinit var adapter: PagerAdapter

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_form)

        vpager = findViewById(R.id.purchasePager)
        val lyinflater : LayoutInflater = layoutInflater
        pagerList = arrayListOf(
                lyinflater.inflate(R.layout.pager_item_purchase_material, null, false),
                lyinflater.inflate(R.layout.pager_item_purchase_parts, null, false)
        )
        this.adapter = CommonPagerAdapter(pagerList)
        vpager.adapter = this.adapter
    }
}