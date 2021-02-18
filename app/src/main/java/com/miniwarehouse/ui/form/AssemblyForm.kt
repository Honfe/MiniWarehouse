package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.miniwarehose.R
import com.miniwarehouse.logic.dbop.AssemblyDbOp
import com.miniwarehouse.ui.listener.AddItemClickListener
import com.miniwarehouse.ui.listener.OtherItemSelectedListener

class AssemblyForm : AppCompatActivity() {

    private lateinit var selfLayout : View
    private lateinit var addItemComponent : ArrayList<View>

    private val dbOp = AssemblyDbOp()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfLayout = layoutInflater.inflate(R.layout.activity_assembly_form, null, false)
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
        val addItemButton = selfLayout.findViewById<View>(R.id.assemblyAddItemBtn) as Button
        val addItemBtnListener = AddItemClickListener(this, selfLayout.findViewById<View>(R.id.dynamicAssemblyAddItem))
                .addSpinnerLine("配件名称", arrayListOf())
                .addEditLine("配件数量")
                .finish()
        addItemButton.setOnClickListener(addItemBtnListener)
        addItemComponent = addItemBtnListener.getComponent()
    }

    private fun initSpinner() {
        // database select result
        val partsNameList = dbOp.getAssemblyNameList()
        val storageList = dbOp.getStorageNameList()
        storageList.add("添加新仓库")
        // 设置Spinner
        if (partsNameList.size > 0) {
            val partsNameSpinner = findViewById<View>(R.id.assemblyItemNameSpinner) as Spinner
            partsNameSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, partsNameList)
        }
        val storageSpinner = findViewById<View>(R.id.assemblyProductLocationSpinner) as Spinner
        storageSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageList)
        // 设置Spinner选择监听器
        val storageItemSelectedListener = OtherItemSelectedListener(this, selfLayout, R.id.dynamicLayoutAssemblyStorageSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        storageSpinner.onItemSelectedListener = storageItemSelectedListener
    }

    private fun registerConstantWidget() {
        val map = mutableMapOf<String, View>(
                "name" to selfLayout.findViewById(R.id.packedPackageNameEdit) as EditText,
                "number" to selfLayout.findViewById(R.id.packedPackageNumberEdit) as EditText,
                "storage" to selfLayout.findViewById(R.id.assemblyProductLocationSpinner) as Spinner,
                "detail" to selfLayout.findViewById(R.id.assemblyTargetDetailEdit) as EditText
        )
        dbOp.bind(map)
    }

    private fun loadDynamicComponent() {
        val storageMap = mutableMapOf<String, View>()
        val storageSpinner = selfLayout.findViewById<View>(R.id.assemblyProductLocationSpinner) as Spinner
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            storageMap["new_storage_name"] = component[0]
            storageMap["new_storage_detail"] = component[1]
        }
        dbOp.bind(storageMap)

        dbOp.setDynamicComponent(addItemComponent.size)
        val assemblyMap = mutableMapOf<String,View>(
                "new_assembly_name_0" to selfLayout.findViewById(R.id.assemblyItemNameSpinner) as Spinner,
                "new_assembly_number_0" to selfLayout.findViewById(R.id.assemblyItemNumberEdit) as EditText
        )
        var i = 0
        while (i++ < addItemComponent.size / 2) {
            assemblyMap["new_assembly_name_$i"] = addItemComponent[(i - 1) * 2]
            assemblyMap["new_assembly_number_$i"] = addItemComponent[(i - 1) * 2 + 1]
        }
        dbOp.bind(assemblyMap)
    }

    private fun initSubmitButton() {
        val submit = selfLayout.findViewById<View>(R.id.assemblySubmitBtn) as Button
        submit.setOnClickListener {
            loadDynamicComponent()
            val dialog = ProgressDialog(this)
            dialog.setMessage("保存中...")
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()
            val res = dbOp.submitData()
            if (dialog.isShowing) dialog.dismiss()
            if (res) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

}