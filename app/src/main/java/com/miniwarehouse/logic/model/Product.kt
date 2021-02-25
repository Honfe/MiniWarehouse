package com.miniwarehouse.logic.model

import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import org.litepal.extension.find

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

    fun updateNumber(): Boolean {
        val list = LitePal.where("name = ? and type = ? and storage_id = ?", this.name, this.type.toString(), this.storage.id.toString()).find<Product>()
        return if (list.isEmpty()) {
            this.save()
        }
        else {
            list[0].number += this.number
            list[0].save()
        }
    }

    fun deleteItself(): Int {
        return LitePal.delete(Product::class.java, this.id)
    }

}