package com.miniwarehouse.logic.repository

import com.miniwarehouse.logic.model.Storage
import org.litepal.LitePal
import org.litepal.extension.findAll

class StorageRepository : RepositoryInterface {

    private lateinit var storageList : List<Storage>

    override fun prepareData() {
        storageList = LitePal.findAll<Storage>()
    }

    override fun getDataList() : List<Storage> = storageList

    override fun getDataNameList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (item in storageList) {
            list.add(item.name)
        }
        return list
    }

    override fun findDataByName(name: String): Storage? {
        for (item in storageList) {
            if (item.name == name) return item
        }
        return null
    }

}