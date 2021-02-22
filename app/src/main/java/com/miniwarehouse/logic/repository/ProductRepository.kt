package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import org.litepal.LitePal
import org.litepal.extension.find

class ProductRepository(private var crossTable : Boolean = false) : RepositoryInterface {

    var conditionNumber = -1

    private lateinit var productList : List<Product>

    override fun prepareData() {
        if (crossTable) {
            val cursor = LitePal.findBySQL(
                    "select p.id as id, p.name as name, p.type as type, p.number as number, s.name as storage, p.detail as detail from product as p, storage as s where p.storage_id=s.id and p.type=2"
            )
            val list = arrayListOf<Product>()
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val product = Product()
                    product.id = cursor.getLong(cursor.getColumnIndex("id"))
                    product.name = cursor.getString(cursor.getColumnIndex("name"))
                    product.type = cursor.getInt(cursor.getColumnIndex("type"))
                    product.number = cursor.getDouble(cursor.getColumnIndex("number"))
                    product.storage.name = cursor.getString(cursor.getColumnIndex("storage"))
                    product.detail = cursor.getString(cursor.getColumnIndex("detail"))
                    list.add(product)
                } while (cursor.moveToNext())
            }
            productList = list
        }
        else
            productList = LitePal.where("type = ? and number > ?", "2", conditionNumber.toString()).find<Product>()
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