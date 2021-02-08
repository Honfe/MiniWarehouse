package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Type
import org.litepal.LitePal
import org.litepal.extension.find

class TypeRepository(private var type: Int) : RepositoryInterface {

    private lateinit var typeList : List<Type>

    override fun prepareData() {
        typeList = LitePal.where("belongTo=?", this.type.toString()).find<Type>()
    }

    override fun getDataList(): List<Type> = typeList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in typeList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Type? {
        for (item in typeList) {
            if (item.name == name) return item
        }
        return null
    }
}