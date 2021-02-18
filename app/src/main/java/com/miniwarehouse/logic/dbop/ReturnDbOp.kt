package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.model.Thing
import com.miniwarehouse.logic.model.Type
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.runInTransaction


class ReturnDbOp : DbOpBase() {

    private val storageRepository = StorageRepository()
    private lateinit var type : Type

    override fun prepareData() {
        storageRepository.prepareData()
        val findRes = LitePal.where("name = ? and belongTo = ?", "配件", "2").find<Type>()
        if (findRes.isEmpty()) {
            type = Type(name = "配件", belongTo = 2)
        }
        else {
            type = findRes[0]
        }
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val detail = registor.getViewItem("detail") as EditText
        val from = registor.getViewItem("from") as EditText
        val date = registor.getViewItem("date") as EditText
        val returnDetail = registor.getViewItem("returnDetail") as EditText

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
            type = type,
            number = number.text.toString().toDouble(),
            isMaterial = false,
            unit = "件",
            storage = storageItem,
            detail = detail.text.toString()
        )

        val shipment = ShipmentInfo(
            receiver = from.text.toString(),
            detail = "退货" + returnDetail.text.toString()
        )

        var result = true
        LitePal.runInTransaction {
            val res1 = thing.save()
            val res2 = shipment.save()
            result = res1 && res2
            result
        }
        return result
    }

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

}