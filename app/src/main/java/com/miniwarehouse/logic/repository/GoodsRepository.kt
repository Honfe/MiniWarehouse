package com.miniwarehouse.logic.repository

import android.util.Log
import com.miniwarehouse.logic.model.Product
import org.litepal.LitePal
import org.litepal.extension.find

class GoodsRepository(private var crossTable : Boolean = false) : RepositoryInterface {

    private lateinit var goodsList : List<Product>

    override fun prepareData() {
        if (crossTable) {
            val cursor = LitePal.findBySQL(
                    "select p.id as id, p.name as name, p.type as type, p.number as number, s.name as storage, p.detail as detail from product as p, storage as s where p.storage_id=s.id and p.type=3"
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
            goodsList = list
        }
        else
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