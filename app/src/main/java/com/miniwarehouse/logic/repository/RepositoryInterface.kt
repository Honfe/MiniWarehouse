package com.miniwarehouse.logic.repository

interface RepositoryInterface {

    fun prepareData()

    fun getDataList() : List<Any>

    fun getDataNameList() : List<Any>

    fun findDataByName(name : String) : Any?

}