package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport

class Storage(
    var id : Long = 0,
    var name : String = "",
    var detail : String = "",
    var materialList : List<Material> = listOf(),
    var productList : List<Product> = listOf()
) : LitePalSupport()
