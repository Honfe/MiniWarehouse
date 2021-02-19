package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.logic.model.Storage
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll

class ShipmentRepository : RepositoryInterface {

    private lateinit var shipmentList : List<ShipmentInfo>

    override fun prepareData() {
        shipmentList = LitePal.findAll<ShipmentInfo>()
    }

    override fun getDataList(): List<ShipmentInfo> = shipmentList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in shipmentList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): ShipmentInfo? {
        for (item in shipmentList) {
            if (item.name == name) return item
        }
        return null
    }

    fun updateItemRepository(target : Product, storage : Storage): Boolean {
        val list = LitePal.where("name = ? and type = ? and storage_id = ?", target.name, target.type.toString(), storage.id.toString()).find<Product>()
        return if (list.isEmpty()) {
            target.save()
        }
        else {
            list[0].number += target.number
            list[0].save()
        }
    }

}