package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.miniwarehose.R
import com.miniwarehouse.ui.listener.AddItemClickListener

class ShipmentForm : AppCompatActivity() {

    private lateinit var selfLayout : View
    private lateinit var addItemComponent : ArrayList<View>

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_shipment_form, null, false)
        setContentView(selfLayout)
        initSpinner()
        initButton()
    }

    private fun initButton() {
        val addItemButton = selfLayout.findViewById<View>(R.id.shipmentAddPackageItemBtn) as Button
        val addItemBtnListener = AddItemClickListener(this, selfLayout.findViewById<View>(R.id.dynamicShipmentLayout))
                .addSpinnerLine("货物名称", arrayListOf())
                .addEditLine("货物数量")
                .addEditMultiLine("货物备注")
                .finish()
        addItemButton.setOnClickListener(addItemBtnListener)
        addItemComponent = addItemBtnListener.getComponent()
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