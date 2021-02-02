package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R
import com.miniwarehouse.ui.listener.StorageItemSelectedListener

class PackedForm : AppCompatActivity() {

    private lateinit var selfLayout : View

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_packed_form, null, false)
        setContentView(selfLayout)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        // todo here
        val productList = ArrayList<String>()
        val storageList = ArrayList<String>()
        storageList.add("添加新仓库")
        // 设置Spinner
        if (productList.size > 0) {
            val productNameSpinner = findViewById<View>(R.id.packedProductNameSpinner) as Spinner
            productNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, productList)
        }
        val storageSpinner = findViewById<View>(R.id.packedPackageLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
        // 设置Spinner选择事件
        storageSpinner.onItemSelectedListener = StorageItemSelectedListener(this, selfLayout, R.id.dynamicLayoutPackageLocationSpinner)
    }
}