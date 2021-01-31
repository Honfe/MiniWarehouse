package com.miniwarehouse.ui.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R

class ReturnProductForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_product_form)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        // todo here
        val storageList = ArrayList<String>()
        storageList.add("添加新仓库")
        val storageSpinner = findViewById<View>(R.id.returnProductLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
    }
}