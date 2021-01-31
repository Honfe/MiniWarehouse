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
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.miniwarehose.R
import com.miniwarehouse.ui.adapter.CommonPagerAdapter
import kotlinx.android.synthetic.main.activity_purchase_form.*

class PurchaseForm : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var vpager : ViewPager
    private lateinit var imgCursor : ImageView
    private lateinit var item_one : TextView
    private lateinit var item_two : TextView

    private lateinit var pagerList : ArrayList<View>
    private lateinit var adpater : CommonPagerAdapter
    private var offset = 0
    private var currIdx = 0
    private var bmpWidth = 0
    private var one = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_form)
        initViews()
    }

    @SuppressLint("InflateParams")
    private fun initViews() {
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
        val lyinflater : LayoutInflater = layoutInflater
        pagerList = arrayListOf(
                lyinflater.inflate(R.layout.pager_item_purchase_material, null, false),
                lyinflater.inflate(R.layout.pager_item_purchase_parts, null, false)
        )
        this.adpater = CommonPagerAdapter(pagerList)
        vpager.adapter = this.adpater
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
