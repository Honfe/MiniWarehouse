package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import com.miniwarehouse.logic.repository.AssemblyRepository
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.runInTransaction

class AssemblyDbOp : DbOpBase() {

    private val assemblyRepository = AssemblyRepository()
    private val storageRepository = StorageRepository()
    private lateinit var type : Type

    private var assemblyCount = 1

    override fun prepareData() {
        assemblyRepository.prepareData()
        storageRepository.prepareData()
        val findRes = LitePal.where("name = ? and belongTo = ?", "配件", "2").find<Type>()
        if (findRes.isEmpty()) {
            type = Type(name = "配件", belongTo = 2)
        }
        else {
            type = findRes[0]
        }
    }

    fun setDynamicComponent(count : Int) {
        assemblyCount = count + 1
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val detail = registor.getViewItem("detail") as EditText

        val storageSpinner = registor.getViewItem("storage") as Spinner
        val storageContent = storageSpinner.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
        }

        val arrayAssemblyName = ArrayList<String>()
        val arrayAssemblyNumber = ArrayList<Double>()

        var index = 0
        while (index < assemblyCount) {
            val nameSpinner = registor.getViewItem("new_assembly_name_$index") as Spinner
            arrayAssemblyName.add(nameSpinner.selectedItem.toString())
            val numberEdit = registor.getViewItem("new_assembly_number_$index") as EditText
            arrayAssemblyNumber.add(numberEdit.text.toString().toDouble())
            ++index
        }

        if (!test(arrayAssemblyName, arrayAssemblyNumber)) return false

        val waitToUpdateThing = ArrayList<Thing>()
        var i = 0
        while (i < arrayAssemblyName.size) {
            val item = assemblyRepository.findDataByName(arrayAssemblyName[i])
            item!!.number -= arrayAssemblyNumber[i]
            waitToUpdateThing.add(item!!)
            ++i
        }

        val thing = Thing(
                name = name.text.toString(),
                type = type,
                number = number.text.toString().toDouble(),
                isMaterial = false,
                unit = "个",
                storage = storageItem,
                detail = detail.text.toString()
        )

        var result = true
        LitePal.runInTransaction {
            for (item in waitToUpdateThing) {
                result = item.save() && result
            }
            val res = thing.save()
            result = result && res
            result
        }
        return result
    }

    private fun test(name : ArrayList<String>, number : ArrayList<Double>): Boolean {
        var i = 0
        while (i < name.size && i < number.size) {
            val assembly = assemblyRepository.findDataByName(name[i])
            if (assembly != null) {
                if (assembly.number - number[i] < 0.0) {
                    return false
                }
            }
            else throw NullPointerException("assembly is not null!!!")
            ++i
        }
        return true
    }

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

    fun getAssemblyNameList() : ArrayList<String> = assemblyRepository.getDataNameList()

}