package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Material
import org.litepal.LitePal
import org.litepal.extension.findAll

class MaterialRepository : RepositoryInterface {

    private lateinit var materialList : List<Material>

    override fun prepareData() {
        materialList = LitePal.findAll<Material>()
    }

    override fun getDataList(): List<Material> = materialList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in materialList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Material? {
        for (item in materialList) {
            if (item.name == name) return item
        }
        return null
    }

}