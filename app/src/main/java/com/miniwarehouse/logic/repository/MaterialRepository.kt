package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Thing
import org.litepal.LitePal
import org.litepal.extension.find

class MaterialRepository : RepositoryInterface {

    private lateinit var materialList : List<Thing>

    override fun prepareData() {
        materialList = LitePal.where("ismaterial=?", "1").find<Thing>()
    }

    override fun getDataList(): List<Thing> = materialList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in materialList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Thing? {
        for (item in materialList) {
            if (item.name == name) return item
        }
        return null
    }

}