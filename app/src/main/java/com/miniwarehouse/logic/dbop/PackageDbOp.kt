package com.miniwarehouse.logic.dbop

import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.Storage
import com.miniwarehouse.logic.repository.ProductRepository
import com.miniwarehouse.logic.repository.StorageRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction

class PackageDbOp : DbOpBase() {

    private val productRepository = ProductRepository()
    private val storageRepository = StorageRepository()

    private var productCount = 1

    override fun prepareData() {
        productRepository.prepareData()
        storageRepository.prepareData()
    }

    fun setDynamicComponent(count : Int) {
        productCount = count + 1
    }

    override fun submitData(): Boolean {
        val name = registor.getViewItem("name") as EditText
        val number = registor.getViewItem("number") as EditText
        val detail = registor.getViewItem("detail") as EditText

        val storageSpinner = registor.getViewItem("storage") as Spinner
        val storageContent = storageSpinner.selectedItem as String
        var storageItem : Storage? = storageRepository.findDataByName(storageContent)
        var newStorage = false
        if (storageItem == null) {
            val storageNameEditText = registor.getViewItem("new_storage_name") as EditText
            val storageDetailsEditText = registor.getViewItem("new_storage_detail") as EditText
            storageItem = Storage(name = storageNameEditText.text.toString(), detail = storageDetailsEditText.text.toString())
            newStorage = true
        }

        val arrayProductName = ArrayList<String>()
        val arrayProductNumber = ArrayList<Double>()

        var index = 0
        while (index < productCount) {
            val nameSpinner = registor.getViewItem("new_product_name_$index") as Spinner
            arrayProductName.add(nameSpinner.selectedItem.toString())
            val numberEdit = registor.getViewItem("new_product_number_$index") as EditText
            arrayProductNumber.add(numberEdit.text.toString().toDouble())
            ++index
        }

        if (!test(arrayProductName, arrayProductNumber)) return false

        val waitToUpdateThing = ArrayList<Product>()
        var i = 0
        while (i < arrayProductName.size) {
            val item = productRepository.findDataByName(arrayProductName[i])
            item!!.number -= arrayProductNumber[i]
            waitToUpdateThing.add(item!!)
            ++i
        }

        val shipment = Product(
                name = name.text.toString(),
                type = 3,
                number = number.text.toString().toDouble(),
                unit = "件",
                storage = storageItem,
                detail = detail.text.toString()
        )

        var result = true
        LitePal.runInTransaction {
            for (item in waitToUpdateThing) {
                result = item.save() && result
            }
            val res1 = if (newStorage)
                storageItem.save()
            else
                true
            val res2 = shipment.save()
            result = result && res1 && res2
            result
        }
        return result
    }

    private fun test(name : ArrayList<String>, number : ArrayList<Double>): Boolean {
        var i = 0
        while (i < name.size && i < number.size) {
            val product = productRepository.findDataByName(name[i])
            if (product != null) {
                if (product.number - number[i] < 0.0) {
                    return false
                }
            }
            else throw NullPointerException("assembly is not null!!!")
            ++i
        }
        return true
    }

    fun getStorageNameList() : ArrayList<String> = storageRepository.getDataNameList()

    fun getProductNameList() : ArrayList<String> = productRepository.getDataNameList()

}