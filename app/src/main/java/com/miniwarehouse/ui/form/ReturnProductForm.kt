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
import com.miniwarehouse.logic.dbop.ReturnDbOp
import com.miniwarehouse.logic.dbop.ShipmentDbOp
import com.miniwarehouse.ui.listener.OtherItemSelectedListener

class ReturnProductForm : AppCompatActivity() {

    private lateinit var selfLayout : View

    private val dbOp = ReturnDbOp()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_return_product_form, null, false)
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
            "name" to selfLayout.findViewById<View>(R.id.returnProductNameEdit),
            "number" to selfLayout.findViewById(R.id.returnProductNumberEdit),
            "storage" to selfLayout.findViewById(R.id.returnProductLocationSpinner),
            "detail" to selfLayout.findViewById(R.id.returnProductDetailEdit),
            "from" to selfLayout.findViewById(R.id.returnFromNameEdit),
            "date" to selfLayout.findViewById(R.id.returnFromDateEdit),
            "returnDetail" to selfLayout.findViewById(R.id.returnFromDetialEdit)
        )
        dbOp.bind(map)
    }

    private fun initSpinner() {
        // database select result
        val storageList = dbOp.getStorageNameList()
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

    private fun initSubmitButton() {
        val submit = selfLayout.findViewById<View>(R.id.returnSubmitBtn) as Button
        submit.setOnClickListener {
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