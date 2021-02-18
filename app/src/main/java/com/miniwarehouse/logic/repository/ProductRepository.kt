package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Product
import org.litepal.LitePal
import org.litepal.extension.find

class ProductRepository : RepositoryInterface {

    private lateinit var productList : List<Product>

    override fun prepareData() {
        productList = LitePal.where("type = ?", "2").find<Product>()
    }

    override fun getDataList(): List<Product> = productList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in productList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Product? {
        for (item in productList) {
            if (item.name == name) return item
        }
        return null
    }

}