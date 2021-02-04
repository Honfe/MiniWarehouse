package com.miniwarehouse.ui.form

import android.annotation.SuppressLint
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
import com.miniwarehouse.ui.adapter.CommonPagerAdapter
import com.miniwarehouse.ui.listener.AddItemClickListener
import com.miniwarehouse.ui.listener.StorageItemSelectedListener
import kotlinx.android.synthetic.main.activity_produce_form.*

class ProduceForm : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var vpager : ViewPager
    private lateinit var imgCursor : ImageView
    private lateinit var item_one : TextView
    private lateinit var item_two : TextView
    private lateinit var addItem1Component : ArrayList<View>
    private lateinit var addItem2Component : ArrayList<View>

    private lateinit var pagerViewList : ArrayList<View>
    private var offset = 0
    private var currIdx = 0
    private var bmpWidth = 0
    private var one = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce_form)
        cachePagerView()
        initSpinner()
        initPager()
        initButton()
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
        // todo here
        val materialItemList = ArrayList<String>()
        val storageItemList = ArrayList<String>()
        storageItemList.add("添加新仓库")
        // 设置Spinner
        if (materialItemList.size > 0) {
            val ASMmaterialSpinner = pagerViewList[0].findViewById<View>(R.id.produceASMPagerMaterialConsumeNameSpinner) as Spinner
            val PDTMasterialSpinner = pagerViewList[1].findViewById<View>(R.id.producePDTPagerMaterialConsumeNameSpinner) as Spinner
            ASMmaterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, materialItemList)
            PDTMasterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, materialItemList)
        }
        val ASMlocationSpinner = pagerViewList[0].findViewById<View>(R.id.produceASMPagerItemLocationSpinner) as Spinner
        val PDTlocationSpinner = pagerViewList[1].findViewById<View>(R.id.producePDTPagerItemLocationSpinner) as Spinner
        ASMlocationSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList)
        PDTlocationSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList)
        // 设置Spinner选择事件监听
        ASMlocationSpinner.onItemSelectedListener = StorageItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutProduceAssemblyStorageSpinner)
        PDTlocationSpinner.onItemSelectedListener = StorageItemSelectedListener(this, pagerViewList[1], R.id.dynamicLayoutProducePartsStorageSpinner)

    }

    private fun initButton() {
        // 配件页面添加项目
        val ASMAddItemButton = pagerViewList[0].findViewById<View>(R.id.produceASMAddMaterialConsumeItem) as Button
        val ASMAddButtonListener = AddItemClickListener(this, pagerViewList[0].findViewById<View>(R.id.dynamicLayoutProduceASMAddItem))
                .addSpinnerLine("原料名称")
                .addEditLine("占        比")
                .finish()
        ASMAddItemButton.setOnClickListener(ASMAddButtonListener)
        addItem1Component = ASMAddButtonListener.getComponent()
        // 产品页面添加项目
        val PDTAddItemButton = pagerViewList[1].findViewById<View>(R.id.producePDTAddMaterialConsumeItem) as Button
        val PDTAddButtonListener = AddItemClickListener(this, pagerViewList[1].findViewById<View>(R.id.dynamicLayoutProducePDTAddItem))
                .addSpinnerLine("原料名称")
                .addEditLine("占        比")
                .finish()
        PDTAddItemButton.setOnClickListener(PDTAddButtonListener)
        addItem2Component = PDTAddButtonListener.getComponent()
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
