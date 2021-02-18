package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Product(
    var id : Long = 0,
    var name : String = "",
    var type : Int = 0,
    var number : Double = 0.0,
    var unit : String = "",
    var storage : Storage = Storage(),
    var detail : String = ""
) : LitePalSupport() {

    fun getTypeName() : String {
        return when (type) {
            1 -> "配件"
            2 -> "成品"
            3 -> "货物"
            else -> "未知"
        }
    }

}