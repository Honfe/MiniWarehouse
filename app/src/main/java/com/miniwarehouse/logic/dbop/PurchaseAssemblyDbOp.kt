package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction

class PurchaseAssemblyDbOp : DbOpBase() {

    private val storageRepository = StorageRepository()

    override fun prepareData() {
        storageRepository.prepareData()
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val unit = registor.getViewItem("unit") as EditText
        val number = registor.getViewItem("number") as EditText
        val details = registor.getViewItem("detail") as EditText

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

        val assembly = Product(
            name = name.text.toString(),
            type = 1,
            number = number.text.toString().toDouble(),
            unit = unit.text.toString(),
            storage = storageItem,
            detail = details.text.toString()
        )

        var result = false
        LitePal.runInTransaction {
            val res1 = if (newStorage)
                storageItem.save()
            else
                true
            val res2 = assembly.save()
            result = res1 && res2
            result
        }
        return result
    }

    fun getStorageNameList(): ArrayList<String> = storageRepository.getDataNameList()

}