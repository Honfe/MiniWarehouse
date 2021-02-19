package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.viewpager.widget.ViewPager
import com.miniwarehose.R
import com.miniwarehouse.logic.dbop.ProduceAssemblyDbOp
import com.miniwarehouse.logic.dbop.ProduceProductDbOp
import com.miniwarehouse.ui.adapter.CommonPagerAdapter
import com.miniwarehouse.ui.listener.AddItemClickListener
import com.miniwarehouse.ui.listener.OtherItemSelectedListener
import kotlinx.android.synthetic.main.activity_produce_form.*

class ProduceForm : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var vpager : ViewPager
    private lateinit var imgCursor : ImageView
    private lateinit var item_one : TextView
    private lateinit var item_two : TextView
    private lateinit var addItem1Component : ArrayList<View>
    private lateinit var addItem2Component : ArrayList<View>
    private var addItemCount1 : Int = 0
    private var addItemCount2 : Int = 0

    private val pager1_db = ProduceAssemblyDbOp()
    private val pager2_db = ProduceProductDbOp()

    private lateinit var pagerViewList : ArrayList<View>
    private var offset = 0
    private var currIdx = 0
    private var bmpWidth = 0
    private var one = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce_form)
        cachePagerView()
        cacheData()
        initSpinner()
        initPager()
        initButton()
        registerConstantWidget()
        initSubmitButton()
    }

    private fun cacheData() {
        pager1_db.prepareData()
        pager2_db.prepareData()
    }

    @SuppressLint("InflateParams")
    private fun cachePagerView() {
        val lyinflater : LayoutInflater = layoutInflater
        pagerViewList = arrayListOf(
                lyinflater.inflate(R.layout.pager_item_produce_assembly, null, false),
                lyinflater.inflate(R.layout.pager_item_produce_product, null, false)
        )
    }

    private fun initSpinner() {
        // database select result
        val materialItemList1 = pager1_db.getMaterialNameList()
        val storageItemList1 = pager1_db.getStorageNameList()
        val materialItemList2 = materialItemList1
        val storageItemList2 = storageItemList1
        storageItemList1.add("添加新仓库")
        // 设置Spinner
        if (materialItemList1.size > 0) {
            val ASMmaterialSpinner = pagerViewList[0].findViewById<View>(R.id.produceASMPagerMaterialConsumeNameSpinner) as Spinner
            val PDTMasterialSpinner = pagerViewList[1].findViewById<View>(R.id.producePDTPagerMaterialConsumeNameSpinner) as Spinner
            ASMmaterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, materialItemList1)
            PDTMasterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, materialItemList2)
        }
        val ASMlocationSpinner = pagerViewList[0].findViewById<View>(R.id.produceASMPagerItemLocationSpinner) as Spinner
        val PDTlocationSpinner = pagerViewList[1].findViewById<View>(R.id.producePDTPagerItemLocationSpinner) as Spinner
        ASMlocationSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList1)
        PDTlocationSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList2)
        // 设置Spinner选择事件监听
        val ASMlocationListener = OtherItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutProduceAssemblyStorageSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        ASMlocationSpinner.onItemSelectedListener = ASMlocationListener
        val PDTlocationListner = OtherItemSelectedListener(this, pagerViewList[1], R.id.dynamicLayoutProducePartsStorageSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        PDTlocationSpinner.onItemSelectedListener = PDTlocationListner
    }

    private fun initButton() {
        // 配件页面添加项目
        val ASMAddItemButton = pagerViewList[0].findViewById<View>(R.id.produceASMAddMaterialConsumeItem) as Button
        val ASMAddButtonListener = AddItemClickListener(this, pagerViewList[0].findViewById<View>(R.id.dynamicLayoutProduceASMAddItem))
                .addSpinnerLine("原料名称", pager1_db.getMaterialNameList())
                .addEditLine("占        比")
                .finish()
        ASMAddItemButton.setOnClickListener(ASMAddButtonListener)
        addItem1Component = ASMAddButtonListener.getComponent()
        addItemCount1 = ASMAddButtonListener.getComponentItemCount()
        // 产品页面添加项目
        val PDTAddItemButton = pagerViewList[1].findViewById<View>(R.id.producePDTAddMaterialConsumeItem) as Button
        val PDTAddButtonListener = AddItemClickListener(this, pagerViewList[1].findViewById<View>(R.id.dynamicLayoutProducePDTAddItem))
                .addSpinnerLine("原料名称", pager2_db.getMaterialNameList())
                .addEditLine("占        比")
                .finish()
        PDTAddItemButton.setOnClickListener(PDTAddButtonListener)
        addItem2Component = PDTAddButtonListener.getComponent()
        addItemCount2 = PDTAddButtonListener.getComponentItemCount()
    }

    private fun initPager() {
        // 控件
        vpager = producePager
        item_one = producePartsItemText
        item_two = produceProductItemText
        imgCursor = produceLineImg
        // 下划线动画相关设置
        bmpWidth = BitmapFactory.decodeResource(resources, R.mipmap.line).width
        val dm = resources.displayMetrics
        val screenWidth = dm.widthPixels
        offset = (screenWidth / 2 - bmpWidth) / 2
        val matrix = Matrix()
        matrix.postTranslate(offset.toFloat(), 0F)
        imgCursor.imageMatrix = matrix
        // 移动距离
        one = offset * 2 + bmpWidth
        // 往viewPager填充View，同时设置点击事件与页面切换事件
        vpager.adapter = CommonPagerAdapter(pagerViewList)
        vpager.currentItem = 0

        item_one.setOnClickListener(this)
        item_two.setOnClickListener(this)

        vpager.addOnPageChangeListener(this)
    }

    private fun initSubmitButton() {
        val pagerSubmit1 = pagerViewList[0].findViewById<View>(R.id.produceASMPagerItemSubmitBtn) as Button
        pagerSubmit1.setOnClickListener {
            loadDynamicComponent1()
            val dialog = ProgressDialog(this)
            dialog.setMessage("保存中……")
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()
            val res = pager1_db.submitData()
            if (dialog.isShowing) dialog.dismiss()
            if (res) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                finish()
            }
            else Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
        }

        val pagerSubmit2 = pagerViewList[1].findViewById<View>(R.id.producePDTPagerItemSubmitBtn) as Button
        pagerSubmit2.setOnClickListener {
            loadDynamicComponent2()
            val dialog = ProgressDialog(this)
            dialog.setMessage("保存中……")
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()
            val res = pager2_db.submitData()
            if (dialog.isShowing) dialog.dismiss()
            if (res) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                finish()
            }
            else Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerConstantWidget() {
        registerConstantWidget1()
        registerConstantWidget2()
    }

    private fun registerConstantWidget1() {
        val map = mutableMapOf<String, View>(
                "name" to pagerViewList[0].findViewById(R.id.produceASMPagerItemTargetNameEdit),
                "number" to pagerViewList[0].findViewById(R.id.produceASMPagerItemTargetNumberEdit),
                "unit" to pagerViewList[0].findViewById(R.id.produceASMPagerItemMaterialUnitWeightEdit),
                "loss_rate" to pagerViewList[0].findViewById(R.id.produceASMPagerItemMaterialLossEdit),
                "storage" to pagerViewList[0].findViewById(R.id.produceASMPagerItemLocationSpinner),
                "detail" to pagerViewList[0].findViewById(R.id.produceASMPagerItemPartsDetailEdit)
        )
        pager1_db.bind(map)
    }

    private fun registerConstantWidget2() {
        val map = mutableMapOf<String, View>(
                "name" to pagerViewList[1].findViewById(R.id.producePDTPagerItemTargetNameEdit),
                "number" to pagerViewList[1].findViewById(R.id.producePDTPagerItemTargetNumberEdit),
                "unit" to pagerViewList[1].findViewById(R.id.producePDTPagerItemMaterialUnitWeightEdit),
                "loss_rate" to pagerViewList[1].findViewById(R.id.producePDTPagerItemMaterialLossEdit),
                "storage" to pagerViewList[1].findViewById(R.id.producePDTPagerItemLocationSpinner),
                "detail" to pagerViewList[1].findViewById(R.id.producePDTPagerItemPartsDetailEdit)
        )
        pager2_db.bind(map)
    }

    private fun loadDynamicComponent1() {
        val storageMap = mutableMapOf<String, View>()
        val storageSpinner = pagerViewList[0].findViewById<View>(R.id.produceASMPagerItemLocationSpinner) as Spinner
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            storageMap["new_storage_name"] = component[0]
            storageMap["new_storage_detail"] = component[1]
        }
        pager1_db.bind(storageMap)

        pager1_db.setDynamicComponent(addItem1Component.size / addItemCount1)
        val materialMap = mutableMapOf<String, View>(
                "new_material_name_0" to pagerViewList[0].findViewById(R.id.produceASMPagerMaterialConsumeNameSpinner),
                "new_material_weight_0" to pagerViewList[0].findViewById(R.id.produceASMMaterialConsumeNumberEdit)
        )
        var i = 0
        while (i++ < addItem1Component.size / addItemCount1) {
            materialMap["new_material_name_$i"] = addItem1Component[(i - 1) * 2]
            materialMap["new_material_weight_$i"] = addItem1Component[(i - 1) * 2 + 1]
        }
        pager1_db.bind(materialMap)
    }

    private fun loadDynamicComponent2() {
        val storageMap = mutableMapOf<String, View>()
        val storageSpinner = pagerViewList[1].findViewById<View>(R.id.producePDTPagerItemLocationSpinner) as Spinner
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            storageMap["new_storage_name"] = component[0]
            storageMap["new_storage_detail"] = component[1]
        }
        pager2_db.bind(storageMap)

        pager2_db.setDynamicComponent(addItem2Component.size / addItemCount2)
        val materialMap = mutableMapOf<String, View>(
                "new_material_name_0" to pagerViewList[1].findViewById(R.id.producePDTPagerMaterialConsumeNameSpinner),
                "new_material_weight_0" to pagerViewList[1].findViewById(R.id.producePDTMaterialConsumeNumberEdit)
        )
        var i = 0
        while (i++ < addItem2Component.size / addItemCount2) {
            materialMap["new_material_name_$i"] = addItem2Component[i * 2]
            materialMap["new_material_weight_$i"] = addItem2Component[i * 2 + 1]
        }
        pager2_db.bind(materialMap)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.producePartsItemText -> vpager.currentItem = 0
                R.id.produceProductItemText -> vpager.currentItem = 1
            }
        }
    }

    override fun onPageSelected(position: Int) {
        lateinit var animation : Animation
        when (position) {
            0 -> if (currIdx == 1) {
                animation = TranslateAnimation(one.toFloat(), 0F, 0F, 0F)
            }
            1 -> if (currIdx == 0) {
                animation = TranslateAnimation(0F, one.toFloat(), 0F, 0F)
            }
        }
        currIdx = position
        animation.fillAfter = true
        animation.duration = 300
        imgCursor.startAnimation(animation)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}
