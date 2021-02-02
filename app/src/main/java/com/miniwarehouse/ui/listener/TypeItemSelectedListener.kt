package com.miniwarehouse.ui.listener

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout

class TypeItemSelectedListener(private val context: Context, private val view: View, private val id: Int)
    : AdapterView.OnItemSelectedListener {

    private var addItem : Boolean = false
    private var firstClick : Boolean = true
    private lateinit var typeEditText : EditText
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
        layout.addView(typeEditText)
    }

    private fun removeComponent() {
        layout.removeView(typeEditText)
    }

    private fun initComponent() {
        typeEditText = EditText(this.context)
        typeEditText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        typeEditText.inputType = EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS
        typeEditText.hint = "新类别名称"
        typeEditText.isSingleLine = true
    }

}