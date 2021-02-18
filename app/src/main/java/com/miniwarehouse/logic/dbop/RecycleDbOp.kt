package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Material
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.repository.MaterialRepository
import com.miniwarehouse.logic.repository.ProductRepository
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction

class RecycleDbOp : DbOpBase() {

    private val productRepository = ProductRepository()
    private val storageRepository = StorageRepository()
    private val materialRepository = MaterialRepository()

    override fun prepareData() {
        productRepository.prepareData()
        storageRepository.prepareData()
        materialRepository.prepareData()
    }

    override fun submitData(): Boolean {
        val productNumber = registor.getViewItem("product_number") as EditText
        val materialName = registor.getViewItem("material_name") as EditText
        val materialNumber = registor.getViewItem("material_number") as EditText
        val materialDetail = registor.getViewItem("material_detail") as EditText

        val storageSpinner = registor.getViewItem("material_storage") as Spinner
        val storageContent = storageSpinner.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailEditText.text.toString())
        }

        val productNameSpinner = registor.getViewItem("product_name") as Spinner
        val productName = productNameSpinner.selectedItem.toString()

        val product = productRepository.findDataByName(productName)
        if (product != null) {
            if (product.number - productNumber.text.toString().toDouble() < 0)
                return false
        }
        else return false

        product.number -= productNumber.text.toString().toDouble()

        var material = materialRepository.findDataByName(materialName.text.toString())
        if (material == null) {
            material = Material(
                    name = materialName.text.toString(),
                    type = "二料",
                    number = materialNumber.text.toString().toDouble(),
                    storage = storageItem,
                    detail = materialDetail.text.toString()
            )
        }

        var result = true
        LitePal.runInTransaction {
            val res1 = material.save()
            val res2 = product.save()
            result = res1 && res2
            result
        }
        return result
    }

    fun getProductNameList() : ArrayList<String> = productRepository.getDataNameList()

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

}