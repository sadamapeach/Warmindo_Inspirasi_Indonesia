package com.android.warmindoinspirasiindonesia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class UpdateAnggotaActivity : AppCompatActivity() {

    private lateinit var ivFoto: ImageView
    private lateinit var tvNamaPengguna: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvRole: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_anggota)

    }
}