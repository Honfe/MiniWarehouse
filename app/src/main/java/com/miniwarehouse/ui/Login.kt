package com.miniwarehouse.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miniwarehose.R
import com.miniwarehouse.ui.menu.MainMenu
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var passwd : String
    private var isFirst : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadData()
        initLoginBtn()
    }

    private fun initLoginBtn() {
        loginBtn.setOnClickListener {
            val pd = passwordEdit.text.toString()
            if (isFirst) {
                if (pd == "" || pd.length != 6) {
                    Toast.makeText(this, "密码不能为空或不为6位", Toast.LENGTH_SHORT).show()
                }
                else {
                    writeData(pd)
                    beginApp()
                }
            }
            else {
                if (pd != passwd) {
                    Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show()
                }
                else {
                    beginApp()
                }
            }
        }
    }

    private fun beginApp() {
        val intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadData() {
        val editor = getSharedPreferences("login", Context.MODE_PRIVATE)
        passwd = editor.getString("passwd", "")!!
        isFirst = editor.getBoolean("isFirst", true)
    }

    @SuppressLint("CommitPrefEdits")
    private fun writeData(passwd : String) {
        val editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit()
        editor.putBoolean("isFirst", false)
        editor.putString("passwd", passwd)
        editor.apply()
    }

}
