package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Product
import org.litepal.LitePal
import org.litepal.extension.find

class GoodsRepository : RepositoryInterface {

    private lateinit var goodsList : List<Product>

    override fun prepareData() {
        goodsList = LitePal.where("type = ?", "3").find<Product>()
    }

    override fun getDataList(): List<Product> = goodsList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in goodsList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Product? {
        for (item in goodsList) {
            if (item.name == name) return item
        }
        return null
    }

}