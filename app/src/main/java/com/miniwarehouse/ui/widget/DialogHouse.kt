package com.miniwarehouse.ui.widget

import android.app.AlertDialog
import android.content.Context
import java.lang.NullPointerException

object DialogHouse {

    fun getConfirmDialog(context: Context, block : () -> Unit): AlertDialog {
        val dialog = AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确定删除吗？")
                .setPositiveButton("确定") { _, _-> block() }
                .setNegativeButton("取消", null)
                .create()
        if (dialog != null) return dialog
        else throw NullPointerException("创建Dialog失败！")
    }

}