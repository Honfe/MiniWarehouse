package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.miniwarehose.R
import com.miniwarehouse.logic.dbop.RecycleDbOp
import com.miniwarehouse.ui.listener.OtherItemSelectedListener

class RecyclerForm : AppCompatActivity() {

    private lateinit var selfLayout : View

    private val dbOp = RecycleDbOp()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_recycler_form, null, false)
        setContentView(selfLayout)
        cacheData()
        initSpinner()
        registerConstantWidget()
        initSubmitButton()
    }

    private fun cacheData() {
        dbOp.prepareData()
    }

    private fun registerConstantWidget() {
        val map = mutableMapOf<String, View>(
                "product_name" to selfLayout.findViewById(R.id.recycleNameSpinner),
                "product_number" to selfLayout.findViewById(R.id.recycleNumberEdit),
                "material_name" to selfLayout.findViewById(R.id.recycleToMaterialName),
                "material_number" to selfLayout.findViewById(R.id.recycleToMaterialNumberEdit),
                "material_storage" to selfLayout.findViewById(R.id.recycleToMaterialLocationSpinner),
                "material_detial" to selfLayout.findViewById(R.id.shipmentPackageItemDetialEdit)
        )
        dbOp.bind(map)
    }

    private fun loadDynamicComponent() {
        val storageMap = mutableMapOf<String, View>()
        val storageSpinner = selfLayout.findViewById<View>(R.id.recycleToMaterialLocationSpinner) as Spinner
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            storageMap["new_storage_name"] = component[0]
            storageMap["new_storage_detail"] = component[1]
        }
        dbOp.bind(storageMap)
    }

    private fun initSpinner() {
        // database select result
        val recycleNameList = dbOp.getProductNameList()
        val storageList = dbOp.getStorageNameList()
        storageList.add("添加新仓库")
        if (recycleNameList.size > 0) {
            val recycleNameSpinner = findViewById<View>(R.id.recycleNameSpinner) as Spinner
            recycleNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, recycleNameList)
        }
        val storageSpinner = findViewById<View>(R.id.recycleToMaterialLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
        // 设置Spinner选择监听器
        val storageItemSelectedListener = OtherItemSelectedListener(this, selfLayout, R.id.dynamicLayoutRecycleLocationSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        storageSpinner.onItemSelectedListener = storageItemSelectedListener
    }

    private fun initSubmitButton() {
        val submit = selfLayout.findViewById<View>(R.id.recycleSubmitBtn) as Button
        submit.setOnClickListener {
            loadDynamicComponent()
            val dialog = ProgressDialog(this)
            dialog.setMessage("保存中")
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