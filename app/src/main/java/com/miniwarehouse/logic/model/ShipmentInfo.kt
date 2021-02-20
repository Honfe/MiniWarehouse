package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport
import java.sql.Date

class ShipmentInfo(
    var id : Long = 0,
    var name : String = "",
    var date : String = "2021-1-1",
    var receiver : String = "",
    var units : String = "",
    var detail : String = ""
) : LitePalSupport()
