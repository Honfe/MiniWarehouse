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
import com.miniwarehouse.ui.listener.StorageItemSelectedListener
import com.miniwarehouse.ui.listener.TypeItemSelectedListener
import kotlinx.android.synthetic.main.activity_purchase_form.*

class PurchaseForm : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var vpager : ViewPager
    private lateinit var imgCursor : ImageView
    private lateinit var item_one : TextView
    private lateinit var item_two : TextView
    private lateinit var pagerViewList : ArrayList<View>

    private var offset = 0
    private var currIdx = 0
    private var bmpWidth = 0
    private var one = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_form)
        cachePagerView()
        initPager()
        initSpinner()
    }

    @SuppressLint("InflateParams")
    private fun cachePagerView() {
        val lyinflater : LayoutInflater = layoutInflater
        pagerViewList = arrayListOf(
            lyinflater.inflate(R.layout.pager_item_purchase_material, null, false),
            lyinflater.inflate(R.layout.pager_item_purchase_parts, null, false)
        )
    }

    private fun initSpinner() {
        // database select result
        // todo here
        val typeItemList = ArrayList<String>()
        val storageItemList = ArrayList<String>()
        typeItemList.add("添加新类型")
        storageItemList.add("添加新仓库")
        // 设置Spinner
        val typeSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialTypeSpinner) as Spinner
        typeSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typeItemList)
        val storageMaterialSpinner = pagerViewList[0].findViewById<View>(R.id.purchasePagerItemMaterialLocationSpinner) as Spinner
        storageMaterialSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList)
        val storagePartsSpinner = pagerViewList[1].findViewById<View>(R.id.purchasePagerItemPartsLocationSpinner) as Spinner
        storagePartsSpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, storageItemList)
        // 设置Spinner选择事件监听
        typeSpinner.onItemSelectedListener = TypeItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutTypeSpinner)
        storageMaterialSpinner.onItemSelectedListener = StorageItemSelectedListener(this, pagerViewList[0], R.id.dynamicLayoutMaterialStorageSpinner)
        storagePartsSpinner.onItemSelectedListener = StorageItemSelectedListener(this, pagerViewList[1], R.id.dynamicLayoutPartsStorageSpinner)
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
