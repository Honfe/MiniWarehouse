package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.miniwarehose.R
import com.miniwarehouse.logic.dbop.ShipmentDbOp
import com.miniwarehouse.ui.listener.AddItemClickListener
import com.miniwarehouse.ui.listener.OtherItemSelectedListener

class ShipmentForm : AppCompatActivity() {

    private lateinit var selfLayout : View
    private lateinit var addItemComponent : ArrayList<View>

    private val dbOp = ShipmentDbOp()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_shipment_form, null, false)
        setContentView(selfLayout)
        cacheData()
        initSpinner()
        initButton()
        registerConstantWidget()
        initSubmitButton()
    }

    private fun cacheData() {
        dbOp.prepareData()
    }

    private fun initButton() {
        val addItemButton = selfLayout.findViewById<View>(R.id.shipmentAddPackageItemBtn) as Button
        val addItemBtnListener = AddItemClickListener(this, selfLayout.findViewById<View>(R.id.dynamicShipmentLayout))
                .addSpinnerLine("货物名称", dbOp.getGoodsNameList())
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

    private fun registerConstantWidget() {
        val map = mutableMapOf<String, View>(
                "receiver" to selfLayout.findViewById(R.id.shipmentInfoRecvEdit),
                "date" to selfLayout.findViewById(R.id.shipmentInfoDateEdit)
        )
        dbOp.bind(map)
    }

    @SuppressLint("CutPasteId")
    private fun loadDynamicComponent() {
        dbOp.setDynamicComponent(addItemComponent.size)
        val goodsMap = mutableMapOf<String, View>(
                "new_goods_name_0" to selfLayout.findViewById(R.id.shipmentPackageItemSpinner) as Spinner,
                "new_goods_number_0" to selfLayout.findViewById(R.id.shipmentPackageItemNumberEdit),
                "new_goods_detail_0" to selfLayout.findViewById(R.id.shipmentPackageItemDetialEdit)
        )
        var i = 0
        while (++i < addItemComponent.size / 3) {
            goodsMap["new_goods_name_$i"] = addItemComponent[(i - 1) * 3]
            goodsMap["new_goods_number_$i"] = addItemComponent[(i - 1) * 3 + 1]
            goodsMap["new_goods_detail_$i"] = addItemComponent[(i - 1) * 3 + 2]
        }
        dbOp.bind(goodsMap)
    }

    private fun initSubmitButton() {
        val submit = selfLayout.findViewById<View>(R.id.shipmentSubmitBtn) as Button
        submit.setOnClickListener {
            loadDynamicComponent()
            val dialog = ProgressDialog(this)
            dialog.setMessage("保存中……")
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()
            val res = dbOp.submitData()
            if (dialog.isShowing) dialog.dismiss()
            if (res) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                finish()
            }
            else Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
        }
    }

}