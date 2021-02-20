package com.miniwarehouse.logic.model

import org.litepal.crud.LitePalSupport
import java.sql.Date

class ShipmentInfo(
    var id : Long = 0,
    var name : String = "",
    var date : String = "2021-1-1",
    var receiver : String = "",
    var status : Int = 0, // 退货->1， 出货->0
    var detail : String = ""
) : LitePalSupport()
