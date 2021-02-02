package com.miniwarehouse.ui.listener

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import java.lang.NullPointerException

class StorageItemSelectedListener(private val context: Context, private val view: View, private val id: Int)
    : AdapterView.OnItemSelectedListener {

    private var addItem : Boolean = false
    private var firstClick : Boolean = true
    private lateinit var firstEditText : EditText
    private lateinit var secondEditText : EditText
    private val layout = this.view.findViewById<View>(this.id) as LinearLayout

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
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
        else throw NullPointerException()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun addComponent() {
        layout.addView(firstEditText)
        layout.addView(secondEditText)
    }

    private fun removeComponent() {
        layout.removeView(firstEditText)
        layout.removeView(secondEditText)
    }

    private fun initComponent() {
        // 仓库名输入框
        firstEditText = EditText(this.context)
        firstEditText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        firstEditText.inputType = EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS
        firstEditText.hint = "新仓库名称"
        firstEditText.isSingleLine = true
        // 仓库信息详情输入框
        secondEditText = EditText(this.context)
        secondEditText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        secondEditText.inputType = EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE
        secondEditText.hint = "新仓库详情"
        secondEditText.setLines(3)
    }

}