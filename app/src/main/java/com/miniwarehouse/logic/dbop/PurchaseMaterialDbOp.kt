package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import com.miniwarehouse.logic.repository.StorageRepository
import com.miniwarehouse.logic.repository.TypeRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction

class PurchaseMaterialDbOp : DbOpBase() {

    private val storageRepository = StorageRepository()
    private val typeRepository = TypeRepository(1)

    override fun prepareData() {
        storageRepository.prepareData()
        typeRepository.prepareData()
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val details = registor.getViewItem("detail") as EditText

        val typeSpinner = registor.getViewItem("type") as Spinner
        val typeContent = typeSpinner.selectedItem as String
        var typeItem : Type? = typeRepository.findDataByName(typeContent)
        if (typeItem == null) {
            val typeEditText = registor.getViewItem("new_type_name") as EditText
            typeItem = Type(name = typeEditText.text.toString(), belongTo = 1)
        }

        val storageSpinner = registor.getViewItem("storage") as Spinner
        val storageContent = storageSpinner.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
        }

        val thing = Thing(
                name = name.text.toString(),
                type = typeItem,
                number = number.text.toString().toDouble(),
                isMaterial = true,
                unit = "0.1g",
                storage = storageItem,
                detail = details.text.toString()
        )

        var result = false
        LitePal.runInTransaction {
            val res1 = typeItem.save()
            val res2 = storageItem.save()
            val res3 = thing.save()
            result = res1 && res2 && res3
            result
        }
        return result
    }

    fun getTypeNameList(): ArrayList<String> = typeRepository.getDataNameList()

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

}