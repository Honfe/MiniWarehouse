package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Product
import org.litepal.LitePal
import org.litepal.extension.find

class AssemblyRepository : RepositoryInterface {

    private lateinit var assemblyList : List<Product>

    override fun prepareData() {
        assemblyList = LitePal.where("type = ?", "1").find<Product>()
    }

    override fun getDataList(): List<Product> = assemblyList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in assemblyList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Product? {
        for (item in assemblyList) {
            if (item.name == name) return item
        }
        return null
    }
}