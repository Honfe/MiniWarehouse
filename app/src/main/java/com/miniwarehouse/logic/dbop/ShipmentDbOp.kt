package com.miniwarehouse.logic.dbop

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import com.miniwarehouse.logic.model.Product
import com.miniwarehouse.logic.model.ShipmentInfo
import com.miniwarehouse.logic.repository.GoodsRepository
import org.litepal.LitePal
import org.litepal.extension.runInTransaction
import java.lang.StringBuilder
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShipmentDbOp : DbOpBase() {

    private val goodsRepository = GoodsRepository()

    private var goodsCount = 1

    override fun prepareData() {
        goodsRepository.prepareData()
    }

    fun setDynamicComponent(count : Int) {
        goodsCount = count + 1
    }

    override fun submitData(): Boolean {
        val receiver = registor.getViewItem("receiver") as EditText
        val date = registor.getViewItem("date") as EditText

        val arrayGoodsName = ArrayList<String>()
        val arrayGoodsNumber = ArrayList<Double>()

        var index = 0
        val shipmentDetailBuilder = StringBuilder()
        while (index < goodsCount) {
            val nameSpinner = registor.getViewItem("new_goods_name_$index") as Spinner
            arrayGoodsName.add(nameSpinner.selectedItem.toString())
            val numberEdit = registor.getViewItem("new_goods_number_$index") as EditText
            arrayGoodsNumber.add(numberEdit.text.toString().toDouble())
            val detailEdit = registor.getViewItem("new_goods_detail_$index") as EditText
            shipmentDetailBuilder
                    .append(arrayGoodsName.last()).append(' ').append("数量：")
                    .append(arrayGoodsNumber.last()).append(' ').append("备注：")
                    .append(detailEdit.text.toString()).append('\n')
            ++index
        }
        val shipmentDetail = shipmentDetailBuilder.toString()

        if (!test(arrayGoodsName, arrayGoodsNumber)) return false

        val waitToUpdateThing = ArrayList<Product>()
        var i = 0
        while (i < arrayGoodsName.size) {
            val item = goodsRepository.findDataByName(arrayGoodsName[i])
            item!!.number -= arrayGoodsNumber[i]
            waitToUpdateThing.add(item!!)
            ++i
        }

        val shipment = ShipmentInfo(
                receiver = receiver.text.toString(),
                date = date.text.toString(),
                status = 0,
                detail = shipmentDetail
        )

        var result = true
        LitePal.runInTransaction {
            for (item in waitToUpdateThing) {
                result = item.save() && result
            }
            val res = shipment.save()
            result = result && res
            result
        }
        return result
    }

    private fun test(name : ArrayList<String>, number : ArrayList<Double>): Boolean {
        var i = 0
        while (i < name.size && i < number.size) {
            val goods = goodsRepository.findDataByName(name[i])
            if (goods != null) {
                if (goods.number - number[i] < 0.0) {
                    return false
                }
            }
            else throw NullPointerException("assembly is not null!!!")
            ++i
        }
        return true
    }

    fun getGoodsNameList() : ArrayList<String> = goodsRepository.getDataNameList()

}