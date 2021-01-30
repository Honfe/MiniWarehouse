package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Thing(
    var id : Long = 0,
    var name : String,
    var type : Type,
    var number : Double = 0.0,
    var isMaterial : Boolean,
    var unit : String,
    var storage : Storage,
    var detail : String = "",
) : LitePalSupport()
