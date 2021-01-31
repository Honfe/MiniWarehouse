package com.miniwarehouse.ui.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.miniwarehose.R

class ShipmentForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipment_form)
        initSpinner()
    }

    private fun initSpinner() {
        // database select result
        val packedNameList = ArrayList<String>()
        if (packedNameList.size > 0) {
            val packageNameSpinner = findViewById<View>(R.id.shipmentPackageItemSpinner) as Spinner
            packageNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, packedNameList)
        }
    }
}