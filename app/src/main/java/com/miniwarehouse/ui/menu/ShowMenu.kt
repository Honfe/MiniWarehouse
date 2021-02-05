package com.miniwarehouse.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miniwarehose.R
import com.miniwarehouse.ui.list.*
import kotlinx.android.synthetic.main.activity_show_menu.*

class ShowMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_menu)

        showMaterialTableBtn.setOnClickListener{
            val intent = Intent(this, MaterialList::class.java)
            startActivity(intent)
        }

        showAssemblyTableBtn.setOnClickListener {
            val intent = Intent(this, AssemblyList::class.java)
            startActivity(intent)
        }

        showProductTableBtn.setOnClickListener {
            val intent = Intent(this, ProductList::class.java)
            startActivity(intent)
        }

        showPackedTableBtn.setOnClickListener {
            val intent = Intent(this, PackedList::class.java)
            startActivity(intent)
        }

        showShipmentTableBtn.setOnClickListener {
            val intent = Intent(this, ShipmentList::class.java)
            startActivity(intent)
        }

    }
}