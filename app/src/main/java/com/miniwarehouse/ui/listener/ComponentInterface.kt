package com.miniwarehouse.ui.listener

import android.view.View

interface ComponentInterface {

    fun getComponent() : ArrayList<View>

    fun getComponentCount() : Int

    fun getComponentItemCount() : Int

}