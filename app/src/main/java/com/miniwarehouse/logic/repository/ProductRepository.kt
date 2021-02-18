package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import org.litepal.LitePal
import org.litepal.extension.find

class ProductRepository : RepositoryInterface {

    private lateinit var productList : List<Thing>

    override fun prepareData() {
        lateinit var type : Type
        val findRes = LitePal.where("name = ? and belongTo = ?", "产品", "2").find<Type>()
        if (findRes.isEmpty()) {
            type = Type(name = "产品", belongTo = 2)
        }
        else {
            type = findRes[0]
        }
        productList = LitePal.where("ismaterial=? and type_id=?", "0", "${type.id}").find<Thing>()
    }

    override fun getDataList(): List<Thing> = productList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in productList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Thing? {
        for (item in productList) {
            if (item.name == name) return item
        }
        return null
    }

}