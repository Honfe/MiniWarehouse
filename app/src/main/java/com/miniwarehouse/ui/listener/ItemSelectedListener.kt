package com.miniwarehouse.ui.listener

import android.util.Log
import android.view.View
import android.widget.AdapterView

open class ItemSelectedListener : AdapterView.OnItemSelectedListener {

    protected var currentSelected = 0
    protected var currentView : String = ""

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        update(parent, position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun getSelectedInfo() : String {
        return currentView
    }

    fun getPosition() : Int {
        return currentSelected
    }

    protected fun update(parent: AdapterView<*>?, position: Int) {
        if (parent != null) {
            currentView = parent.getItemAtPosition(position) as String
        }
        currentSelected = position
    }

}