package com.miniwarehouse.logic.model

import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import org.litepal.extension.find

class Material(
    var id : Long = 0,
    var name : String = "",
    var type : String = "",
    var number : Double = 0.0,
    var storage : Storage = Storage(),
    var detail : String = ""
) : LitePalSupport() {

    fun updateNumber(): Boolean {
        val list = LitePal.where("name = ? and type = ? and storage_id = ?", this.name, this.type, this.storage.id.toString()).find<Material>()
        return if (list.isEmpty()) {
            this.save()
        }
        else {
            list[0].number += number
            list[0].save()
        }
    }

}
