package com.android.warmindoinspirasiindonesia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnPengguna: Button
    private lateinit var btnRole: Button
    private lateinit var btnTransaksi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnPengguna = findViewById(R.id.btn_pengguna)
        btnRole = findViewById(R.id.btn_role)
        btnTransaksi = findViewById(R.id.btn_transaksi)

        btnRole.setOnClickListener(this)
        btnPengguna.setOnClickListener(this)
        btnTransaksi.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_role) {
                val crudRoleIntent = Intent(this, CRUDRoleActivity::class.java)
                startActivity(crudRoleIntent)
            } else if (view.id == R.id.btn_pengguna) {
                val crudPenggunaIntent = Intent(this, CRUDPenggunaActivity::class.java)
                startActivity(crudPenggunaIntent)
            } else if (view.id == R.id.btn_transaksi) {
                val addTransaksiActivity = Intent(this, AddTransaksiActivity::class.java)
                startActivity(addTransaksiActivity)
            }
        }
    }
}