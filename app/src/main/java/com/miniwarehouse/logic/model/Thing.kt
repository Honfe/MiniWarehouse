package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Thing(
    var id : Long = 0,
    var name : String = "",
    var type : Type = Type(),
    var number : Double = 0.0,
    var isMaterial : Boolean = false,
    var unit : String = "",
    var storage : Storage = Storage(),
    var detail : String = "",
) : LitePalSupport()
