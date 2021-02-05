package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R
import com.miniwarehouse.ui.listener.OtherItemSelectedListener

class ReturnProductForm : AppCompatActivity() {

    private lateinit var selfLayout : View

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_return_product_form, null, false)
        setContentView(selfLayout)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        // todo here
        val storageList = ArrayList<String>()
        storageList.add("添加新仓库")
        val storageSpinner = findViewById<View>(R.id.returnProductLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
        // 设置Spinner选择监听器
        val storageItemSelectedListener = OtherItemSelectedListener(this, selfLayout, R.id.dynamicLayoutReturnSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        storageSpinner.onItemSelectedListener = storageItemSelectedListener
    }
}