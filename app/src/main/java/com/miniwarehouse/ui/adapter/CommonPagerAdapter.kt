package com.miniwarehouse.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class CommonPagerAdapter(private val viewIdList: ArrayList<View>) : PagerAdapter() {

    override fun getCount(): Int {
        return viewIdList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewIdList[position])
        return viewIdList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewIdList[position])
    }

}