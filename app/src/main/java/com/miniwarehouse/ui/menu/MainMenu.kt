package com.miniwarehouse.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miniwarehose.R
import com.miniwarehouse.ui.form.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        intoShowMenuBtn.setOnClickListener {
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }

        intoPurchaseMenuBtn.setOnClickListener {
            val intent = Intent(this, PurchaseForm::class.java)
            startActivity(intent)
        }

        intoProductMenuBtn.setOnClickListener {
            val intent = Intent(this, ProduceForm::class.java)
            startActivity(intent)
        }

        intoAssemblyMenuBtn.setOnClickListener {
            val intent = Intent(this, AssemblyForm::class.java)
            startActivity(intent)
        }

        intoPackedBtn.setOnClickListener {
            val intent = Intent(this, PackedForm::class.java)
            startActivity(intent)
        }

        intoShipmentBtn.setOnClickListener {
            val intent = Intent(this, ShipmentForm::class.java)
            startActivity(intent)
        }

        intoRecycleProductBtn.setOnClickListener {
            val intent = Intent(this, RecyclerForm::class.java)
            startActivity(intent)
        }

        intoReturnProductBtn.setOnClickListener {
            val intent = Intent(this, ReturnProductForm::class.java)
            startActivity(intent)
        }

    }
}