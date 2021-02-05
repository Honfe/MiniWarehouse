package com.miniwarehouse.ui.listener

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout

class OtherItemSelectedListener(private val context: Context, private val view: View, private val id: Int)
    : ItemSelectedListener(), ComponentInterface {

    private var addItem : Boolean = false
    private var firstClick : Boolean = true
    val arrayItemLabelList = ArrayList<String>()
    private val arrayItemTitleList = ArrayList<String>()
    private val layout = this.view.findViewById<View>(this.id) as LinearLayout
    val componentList = ArrayList<View>()
    var itemCount : Int = 0

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            update(parent, position)
            if (itemCount <= 0) {
                return
            }
            if (parent.count == position + 1) {
                if (!addItem) {
                    if (firstClick) {
                        initComponent()
                        firstClick = false
                    }
                    addComponent()
                    addItem = true
                }
            }
            else {
                if (addItem) {
                    removeComponent()
                    addItem = false
                }
            }
        }
    }

    fun addEditLine(item : String): OtherItemSelectedListener {
        arrayItemTitleList.add(item)
        arrayItemLabelList.add("edit")
        return this
    }

    fun addEditMultiLine(item : String): OtherItemSelectedListener {
        arrayItemTitleList.add(item)
        arrayItemLabelList.add("multiEdit")
        return this
    }

    fun finish(): OtherItemSelectedListener {
        itemCount = arrayItemLabelList.size
        return this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun getComponent(): ArrayList<View> {
        return componentList
    }

    override fun getComponentCount(): Int {
        return 1
    }

    override fun getComponentItemCount(): Int {
        return itemCount
    }

    private fun initComponent() {
        var i = 0
        while (i < itemCount) {
            componentList.add(when (arrayItemLabelList[i]) {
                "edit" -> editLine(arrayItemTitleList[i])
                else -> editMultiLine(arrayItemTitleList[i])
            })
            ++i
        }
    }

    private fun editLine(item : String): EditText {
        val editText = EditText(this.context)
        editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.inputType = EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS
        editText.hint = item
        editText.isSingleLine = true
        return editText
    }

    private fun editMultiLine(item : String): EditText {
        val editText = EditText(this.context)
        editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText.inputType = EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE
        editText.hint = item
        editText.setLines(3)
        return editText
    }

    private fun addComponent() {
        for (item in componentList) {
            layout.addView(item)
        }
    }

    private fun removeComponent() {
        for (item in componentList) {
            layout.removeView(item)
        }
    }

}