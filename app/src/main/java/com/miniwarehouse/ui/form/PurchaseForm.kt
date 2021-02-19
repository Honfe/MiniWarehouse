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
import com.miniwarehouse.logic.dbop.PurchaseAssemblyDbOp
import com.miniwarehouse.logic.dbop.PurchaseMaterialDbOp
import com.miniwarehouse.ui.adapter.CommonPagerAdapter
import com.miniwarehouse.ui.listener.OtherItemSelectedListener
import kotlinx.android.synthetic.main.activity_purchase_form.*

class PurchaseForm : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var vpager : ViewPager
    private lateinit var imgCursor : ImageView
    private lateinit var item_one : TextView
    private lateinit var item_two : TextView
    private lateinit var pagerViewList : ArrayList<View>

    private val pager1_db = PurchaseMaterialDbOp()
    private val pager2_db = PurchaseAssemblyDbOp()

    private var offset = 0
    private var currIdx = 0
    private var bmpWidth = 0
    private var one = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_form)
        cachePagerView()
        cacheData()
        initPager()
        initSpinner()
        registerConstantWidget()
        initSubmitButton()
    }

    private fun registerConstantWidget() {
        registerConstantWidget1()
        registerConstantWidget2()
    }

    @SuppressLint("InflateParams")
    private fun cachePagerView() {
        val lyinflater : LayoutInflater = layoutInflater
        pagerViewList = arrayListOf(
            lyinflater.inflate(R.layout.pager_item_purchase_material, null, false),
            lyinflater.inflate(R.layout.pager_item_purchase_parts, null, false)
        )
    }

    private fun cacheData() {
        pager1_db.prepareData()
        pager2_db.prepareData()
    }

    private fun initSpinner() {
        // database select result
        val typeItemList = pager1_db.getMaterialTypeNameList()
        val storageItemList1 = pager1_db.getStorageNameList()
        val storageItemList2 = pager2_db.getStorageNameList()
        typeItemList.add("添加新类型")
        storageItemList1.add("添加新仓库")
        storageItemList2.add("添加新仓库")
        // 设置Spinner
        val typeSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialTypeSpinner) as Spinner
        typeSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typeItemList)
        val storageMaterialSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialLocationSpinner) as Spinner
        storageMaterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList1)
        val storagePartsSpinner = pagerViewList[1].findViewById<View>(R.id.purchasePagerItemPartsLocationSpinner) as Spinner
        storagePartsSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList2)
        // 设置Spinner选择事件监听
        val typeItemSelectedListener = OtherItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutTypeSpinner)
                .addEditLine("新类型名称")
                .finish()
        typeSpinner.onItemSelectedListener = typeItemSelectedListener
        val storageASMItemSelectedListener = OtherItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutMaterialStorageSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        storageMaterialSpinner.onItemSelectedListener = storageASMItemSelectedListener
        val storagePRTItemSelectedListner = OtherItemSelectedListener(this, pagerViewList[1], R.id.dynamicLayoutPartsStorageSpinner)
                .addEditLine("新仓库名称")
                .addEditMultiLine("新仓库详情")
                .finish()
        storagePartsSpinner.onItemSelectedListener = storagePRTItemSelectedListner
    }

    private fun initPager() {
        // 控件
        vpager = purchasePager
        item_one = purchaseMaterialItemText
        item_two = purchasePartsItemText
        imgCursor = purchaseLineImg
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
        // 往ViewPager填充View，同时设置点击事件与页面切换事件
        vpager.adapter = CommonPagerAdapter(pagerViewList)
        vpager.currentItem = 0

        item_one.setOnClickListener(this)
        item_two.setOnClickListener(this)

        vpager.addOnPageChangeListener(this)
    }

    private fun registerConstantWidget1() {
        val map = mutableMapOf<String, View>(
                "name" to pagerViewList[0].findViewById(R.id.purchasePagerItemMaterialNameEdit),
                "type" to pagerViewList[0].findViewById(R.id.purchasePagerItemMaterialTypeSpinner),
                "number" to pagerViewList[0].findViewById(R.id.purchasePagerItemMaterialNumberEdit),
                "storage" to pagerViewList[0].findViewById(R.id.purchasePagerItemMaterialLocationSpinner),
                "detail" to pagerViewList[0].findViewById(R.id.purchasePagerItemMaterialDetailEdit)
        )
        pager1_db.bind(map)
    }

    private fun registerConstantWidget2() {
        val map = mutableMapOf<String, View>(
                "name" to pagerViewList[1].findViewById(R.id.purchasePagerItemPartsNameEdit),
                "unit" to pagerViewList[1].findViewById(R.id.purchasePagerItemPartsUnitEdit),
                "number" to pagerViewList[1].findViewById(R.id.purchasePagerItemPartsNumberEdit),
                "storage" to pagerViewList[1].findViewById(R.id.purchasePagerItemPartsLocationSpinner),
                "detail" to pagerViewList[1].findViewById(R.id.purchasePagerItemPartsDetailEdit)
        )
        pager2_db.bind(map)
    }

    fun initSubmitButton() {
        val pagerSubmit1 = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialSubmitBtn) as Button
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

        val pagerSubmit2 = pagerViewList[1].findViewById<View>(R.id.purchasePagerItemPartsSubmitBtn) as Button
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

    private fun loadDynamicComponent1() {
        val typeSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialTypeSpinner) as Spinner
        val map = mutableMapOf<String, View>()
        if (typeSpinner.selectedItemPosition + 1 == typeSpinner.count) {
            val listener = typeSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            map["new_type_name"] = component[0]
        }
        val storageSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialLocationSpinner) as Spinner
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            map["new_storage_name"] = component[0]
            map["new_storage_detail"] = component[1]
        }
        pager1_db.bind(map)
    }

    private fun loadDynamicComponent2() {
        val storageSpinner = pagerViewList[1].findViewById<View>(R.id.purchasePagerItemPartsLocationSpinner) as Spinner
        val map = mutableMapOf<String, View>()
        if (storageSpinner.selectedItemPosition + 1 == storageSpinner.count) {
            val listener = storageSpinner.onItemSelectedListener as OtherItemSelectedListener
            val component = listener.getComponent()
            map["new_storage_name"] = component[0]
            map["new_storage_detail"] = component[1]
        }
        pager2_db.bind(map)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.purchaseMaterialItemText -> vpager.currentItem = 0
                R.id.purchasePartsItemText -> vpager.currentItem = 1
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
        animation.fillAfter = true  // 图片停留在动画结束处
        animation.duration = 300    // 动画时间300ms
        imgCursor.startAnimation(animation)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}
