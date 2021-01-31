package com.miniwarehouse.ui.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R

class AssemblyForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly_form)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        // todo here
        val partsNameList = ArrayList<String>()
        val storageList = ArrayList<String>()
        storageList.add("添加新仓库")
        // 设置Spinner
        if (partsNameList.size > 0) {
            val partsNameSpinner = findViewById<View>(R.id.assemblyItemNameSpinner) as Spinner
            partsNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, partsNameList)
        }
        val storageSpinner = findViewById<View>(R.id.assemblyProductLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
    }
}