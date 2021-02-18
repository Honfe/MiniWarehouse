package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Material(
    var id : Long = 0,
    var name : String = "",
    var type : String = "",
    var number : Double = 0.0,
    var storage : Storage = Storage(),
    var detail : String = ""
) : LitePalSupport()
