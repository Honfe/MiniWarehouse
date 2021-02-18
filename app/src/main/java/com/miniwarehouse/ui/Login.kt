package com.miniwarehouse.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miniwarehose.R
import com.miniwarehouse.ui.menu.MainMenu
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
    }
}