package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import com.miniwarehouse.logic.repository.MaterialRepository
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.runInTransaction
import java.lang.NullPointerException

class ProduceProductDbOp : DbOpBase() {

    private val materialRepository = MaterialRepository()
    private val storageRepository = StorageRepository()
    private lateinit var type : Type

    private var materialCount = 1

    override fun prepareData() {
        materialRepository.prepareData()
        storageRepository.prepareData()
        val findRes = LitePal.where("name = ? and belongTo = ?", "产品", "2").find<Type>()
        if (findRes.isEmpty()) {
            type = Type(name = "产品", belongTo = 2)
        }
        else {
            type = findRes[0]
        }
    }

    fun setDynamicComponent(count : Int) {
        materialCount = count + 1
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val unit = registor.getViewItem("unit") as EditText
        val lossRate = registor.getViewItem("loss_rate") as EditText
        val detail = registor.getViewItem("detail") as EditText

        val storageSpinenr = registor.getViewItem("storage") as Spinner
        val storageContent = storageSpinenr.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
        }

        val materialSum = (number.text.toString().toInt() * unit.text.toString().toDouble()) / lossRate.text.toString().toDouble()

        val arrayMaterialName = ArrayList<String>()
        val arrayMaterialWeight = ArrayList<Double>()

        var index = 0
        var sumWeight = 0.0
        while (index < materialCount) {
            val nameSpinner = registor.getViewItem("new_material_name_$index") as Spinner
            arrayMaterialName.add(nameSpinner.selectedItem.toString())
            val weightEdit = registor.getViewItem("new_material_weight_$index") as EditText
            val temp = weightEdit.text.toString().toDouble()
            sumWeight += temp
            arrayMaterialWeight.add(temp)
            ++index
        }

        if (!test(arrayMaterialName, arrayMaterialWeight, sumWeight, materialSum)) {
            return false
        }
        val waitToUpdateThing = ArrayList<Thing>()
        var i = 0
        while (i < arrayMaterialName.size) {
            val item = materialRepository.findDataByName(arrayMaterialName[i])
            item!!.number -= materialSum * (arrayMaterialWeight[i] / sumWeight)
            waitToUpdateThing.add(item!!)
            ++i
        }

        val thing = Thing(
                name = name.text.toString(),
                type = type,
                number = number.text.toString().toDouble(),
                isMaterial = false,
                unit = unit.text.toString(),
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

    private fun test(name : ArrayList<String>, rate : ArrayList<Double>, sumWeight : Double, sum : Double): Boolean {
        var i = 0
        while (i < name.size && i < rate.size) {
            val material = materialRepository.findDataByName(name[i])
            val itemNumber = sum * (rate[i] / sumWeight)
            if (material != null) {
                if (material.number - itemNumber < 0) {
                    return false
                }
            }
            else throw NullPointerException("material is null!!!")
            ++i
        }
        return true
    }

    fun getMaterialNameList(): ArrayList<String> = materialRepository.getDataNameList()

    fun getStorageNameList(): ArrayList<String> = storageRepository.getDataNameList()

}