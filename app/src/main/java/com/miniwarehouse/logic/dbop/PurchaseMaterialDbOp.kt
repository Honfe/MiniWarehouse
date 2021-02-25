package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.repository.MaterialRepository
import com.miniwarehouse.logic.repository.MaterialTypeRepository
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction

class PurchaseMaterialDbOp : DbOpBase() {

    private val storageRepository = StorageRepository()
    private val typeRepository = MaterialTypeRepository()

    override fun prepareData() {
        storageRepository.prepareData()
        typeRepository.prepareData()
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val details = registor.getViewItem("detail") as EditText

        val typeSpinner = registor.getViewItem("type") as Spinner
        val typeItem = if (typeSpinner.selectedItemPosition + 1 == typeSpinner.count) {
            val typeEditText = registor.getViewItem("new_type_name") as EditText
            typeEditText.text.toString()
        } else {
            typeSpinner.selectedItem.toString()
        }

        val storageSpinner = registor.getViewItem("storage") as Spinner
        val storageContent = storageSpinner.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        var newStorage = false
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
            newStorage = true
        }

        val material = Material(
            name = name.text.toString(),
            type = typeItem,
            number = number.text.toString().toDouble(),
            storage = storageItem,
            detail = details.text.toString()
        )

        var result = false
        LitePal.runInTransaction {
            val res1 = if (newStorage)
                storageItem.save()
            else
                true
            val res2 = material.updateNumber()
            result = res1 && res2
            result
        }
        return result
    }

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

    fun getMaterialTypeNameList() : ArrayList<String> = typeRepository.getDataNameList()

}