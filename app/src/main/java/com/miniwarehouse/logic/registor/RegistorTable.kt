package com.miniwarehouse.logic.registor

import android.view.View

class RegistorTable {

    private var viewMap = mutableMapOf<String, View>()

    fun loadViewMap(map : MutableMap<String, View>) {
        for (view in map) {
            loadViewItem(view.key, view.value)
        }
    }

    fun loadViewItem(key : String, value : View) {
        viewMap[key] = value
    }

    fun getViewItem(key: String) : View? = viewMap[key]

}