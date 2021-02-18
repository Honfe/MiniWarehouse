package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import org.litepal.LitePal
import org.litepal.extension.find

class AssemblyRepository : RepositoryInterface {

    private lateinit var assemblyList : List<Thing>

    override fun prepareData() {
        lateinit var type: Type
        val findRes = LitePal.where("name = ? and belongTo = ?", "配件", "2").find<Type>()
        if (findRes.isEmpty()) {
            type = Type(name = "配件", belongTo = 2)
        }
        else {
            type = findRes[0]
        }
        assemblyList = LitePal.where("ismaterial=? and type_id=?", "0", "${type.id}").find<Thing>()
    }

    override fun getDataList(): List<Thing> = assemblyList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in assemblyList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Thing? {
        for (item in assemblyList) {
            if (item.name == name) return item
        }
        return null
    }
}