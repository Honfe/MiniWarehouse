package com.miniwarehouse.ui.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R

class RecyclerForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_form)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        val recycleNameList = ArrayList<String>()
        val storageList = ArrayList<String>()
        storageList.add("添加新仓库")
        if (recycleNameList.size > 0) {
            val recycleNameSpinner = findViewById<View>(R.id.recycleNameSpinner) as Spinner
            recycleNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, recycleNameList)
        }
        val storageSpinner = findViewById<View>(R.id.recycleToMaterialLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
    }
}