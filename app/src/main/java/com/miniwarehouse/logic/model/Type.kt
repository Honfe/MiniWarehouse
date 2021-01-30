package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Type(
    var id : Long = 0,
    var name : String,
    var belongTo : Int,
    var ThingList : List<Thing> = listOf()
) : LitePalSupport()
