package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction
import java.sql.Date


class ReturnDbOp : DbOpBase() {

    private val storageRepository = StorageRepository()

    override fun prepareData() {
        storageRepository.prepareData()
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
        var newStorage = false
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
            newStorage = true
        }

        val product = Product(
            name = name.text.toString(),
            type = 3,
            number = number.text.toString().toDouble(),
            unit = "件",
            storage = storageItem,
            detail = detail.text.toString()
        )

        val shipment = ShipmentInfo(
            receiver = from.text.toString(),
            date = Date.valueOf(date.text.toString()),
            detail = "退货" + returnDetail.text.toString()
        )

        var result = true
        LitePal.runInTransaction {
            val res3 = if (newStorage)
                storageItem.save()
            else
                true
            val res1 = product.save()
            val res2 = shipment.save()
            result = res1 && res2 && res3
            result
        }
        return result
    }

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

}