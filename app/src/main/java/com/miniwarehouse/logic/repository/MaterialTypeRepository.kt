package com.miniwarehouse.logic.repository

import org.litepal.LitePal
import org.litepal.LitePal.findBySQL

class MaterialTypeRepository : RepositoryInterface {

    private val typeList = arrayListOf<String>()

    override fun prepareData() {
        val cursor = findBySQL("SELECT DISTINCT type FROM material")
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val typeItem = cursor.getString(cursor.getColumnIndex("type"))
                typeList.add(typeItem)
            } while (cursor.moveToNext())
        }
    }

    override fun getDataList(): List<String> = typeList

    override fun getDataNameList(): ArrayList<String> = typeList

    override fun findDataByName(name: String): String? {
        for (item in typeList) {
            if (item == name) return item
        }
        return null
    }

}