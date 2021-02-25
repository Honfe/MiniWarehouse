package com.miniwarehouse.logic.repository

import android.util.Log
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll

class MaterialRepository(private var crossTable : Boolean = false) : RepositoryInterface {

    var conditionNumber = -1

    private lateinit var materialList : List<Material>

    override fun prepareData() {
        if (crossTable) {
            val cursor = LitePal.findBySQL(
                    "select m.id as id, m.name as name, m.type as type, m.number as number, s.name as storage, m.detail as detail from material as m, storage as s where m.storage_id=s.id"
            )
            val list = arrayListOf<Material>()
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val material = Material()
                    material.id = cursor.getLong(cursor.getColumnIndex("id"))
                    material.name = cursor.getString(cursor.getColumnIndex("name"))
                    material.type = cursor.getString(cursor.getColumnIndex("type"))
                    material.number = cursor.getDouble(cursor.getColumnIndex("number"))
                    material.storage.name = cursor.getString(cursor.getColumnIndex("storage"))
                    material.detail = cursor.getString(cursor.getColumnIndex("detail"))
                    list.add(material)
                } while (cursor.moveToNext())
            }
            materialList = list
        }
        else
            materialList = LitePal.where("number > ?", conditionNumber.toString()).find<Material>()
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