package com.miniwarehouse.ui.listener

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

class AddItemClickListener(private var context: Context, private var view: View)
    : View.OnClickListener, ComponentInfo {

    val componentList = ArrayList<View>()
    private val arrayItemLabelList = ArrayList<String>()
    private val arrayItemNameList = ArrayList<String>()
    var itemCount : Int = 0
    var layoutCount : Int = 0

    override fun onClick(v: View?) {
        val layout = initLayout()
        var i = 0
        while (i < itemCount) {
            layout.addView(when (arrayItemLabelList[i]) {
                "spinner" -> spinnerLine(arrayItemNameList[i])
                "edit" -> editLine(arrayItemNameList[i])
                "multiEdit" ->editMultiLine(arrayItemNameList[i])
                else -> null
            })
            ++i
        }
        addComponent(layout)
        ++layoutCount
    }

    fun addSpinnerLine(item : String): AddItemClickListener {
        arrayItemNameList.add(item)
        arrayItemLabelList.add("spinner")
        return this
    }

    fun addEditLine(item : String): AddItemClickListener {
        arrayItemNameList.add(item)
        arrayItemLabelList.add("edit")
        return this
    }

    fun addEditMultiLine(item : String): AddItemClickListener {
        arrayItemNameList.add(item)
        arrayItemLabelList.add("multiEdit")
        return this
    }

    fun finish(): AddItemClickListener {
        itemCount = arrayItemLabelList.size
        return this
    }

    override fun getComponent(): ArrayList<View> {
        return componentList
    }

    override fun getComponentCount(): Int {
        return layoutCount
    }

    override fun getComponentItemCount(): Int {
        return itemCount
    }

    private fun initLayout(): LinearLayout {
        val layout = LinearLayout(context)
        layout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.orientation = LinearLayout.VERTICAL
        return layout
    }

    private fun spinnerLine(item : String): LinearLayout {
        val layoutItem = LinearLayout(context)
        layoutItem.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutItem.orientation = LinearLayout.HORIZONTAL
        // 文本框
        val textViewItem = TextView(context)
        textViewItem.layoutParams = LinearLayout.LayoutParams( 0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0F)
        textViewItem.text = item
        textViewItem.setTextColor(Color.BLACK)
        textViewItem.textSize = 20F
        layoutItem.addView(textViewItem)
        // 下拉框
        val spinnerItem = Spinner(context)
        spinnerItem.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3.0F)
        layoutItem.addView(spinnerItem)
        // 添加入项
        return layoutItem
    }

    private fun editLine(item : String): LinearLayout {
        val layoutItem = LinearLayout(context)
        layoutItem.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutItem.orientation = LinearLayout.HORIZONTAL
        // 文本框
        val textViewItem = TextView(context)
        textViewItem.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        textViewItem.text = item
        textViewItem.setTextColor(Color.BLACK)
        textViewItem.textSize = 20F
        layoutItem.addView(textViewItem)
        // 输入框
        val editTextItem = EditText(context)
        editTextItem.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3F)
        editTextItem.inputType = EditorInfo.TYPE_CLASS_NUMBER
        editTextItem.isSingleLine = true
        layoutItem.addView(editTextItem)
        // 添加入项目
        return layoutItem
    }

    private fun editMultiLine(item : String): LinearLayout {
        val layoutItem = LinearLayout(context)
        layoutItem.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutItem.orientation = LinearLayout.HORIZONTAL
        // 文本框
        val textViewItem = TextView(context)
        textViewItem.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        textViewItem.text = item
        textViewItem.setTextColor(Color.BLACK)
        textViewItem.textSize = 20F
        layoutItem.addView(textViewItem)
        // 输入框
        val editTextItem = EditText(context)
        editTextItem.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3F)
        editTextItem.inputType = EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE
        editTextItem.setLines(3)
        layoutItem.addView(editTextItem)
        // 添加入项目
        return layoutItem
    }

    private fun addComponent(layout : View) {
        componentList.add(layout)
        val linearLayout = view as LinearLayout
        linearLayout.addView(layout)
    }

}
