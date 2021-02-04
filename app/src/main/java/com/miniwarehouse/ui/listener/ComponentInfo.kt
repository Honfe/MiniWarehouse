package com.miniwarehouse.ui.listener

import android.view.View

interface ComponentInfo {

    fun getComponent() : ArrayList<View>

    fun getComponentCount() : Int

}