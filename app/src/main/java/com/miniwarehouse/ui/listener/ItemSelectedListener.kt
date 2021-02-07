package com.miniwarehouse.ui.listener

import android.view.View
import android.widget.AdapterView

open class ItemSelectedListener : AdapterView.OnItemSelectedListener {

    protected var currentSelected = 0
    protected var currentInfo : String = ""

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        update(parent, position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun getSelectedInfo() : String {
        return currentInfo
    }

    fun getPosition() : Int {
        return currentSelected
    }

    protected fun update(parent: AdapterView<*>?, position: Int) {
        if (parent != null) {
            currentInfo = parent.getItemAtPosition(position) as String
        }
        currentSelected = position
    }

}