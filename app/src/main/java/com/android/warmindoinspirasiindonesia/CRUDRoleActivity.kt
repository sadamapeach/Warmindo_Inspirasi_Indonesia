package com.android.warmindoinspirasiindonesia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class CRUDRoleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudrole)

        btnAdd = findViewById(R.id.btn_add)

        btnAdd.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                val addRoleIntent = Intent(this, AddRoleActivity::class.java)
                startActivity(addRoleIntent)
            }
        }
    }
}
