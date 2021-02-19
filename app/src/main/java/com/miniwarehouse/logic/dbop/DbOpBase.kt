package com.miniwarehouse.logic.dbop

import android.view.View
import com.miniwarehouse.logic.registor.RegistorTable

abstract class DbOpBase {

    protected val registor = RegistorTable()

    fun bind(
            viewMap : MutableMap<String, View> = mutableMapOf()) {
        registor.loadViewMap(viewMap)
    }

    abstract fun prepareData()

    abstract fun submitData() : Boolean

}