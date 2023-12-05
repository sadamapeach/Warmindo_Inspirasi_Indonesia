package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTransaksiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etIDTransaksi: EditText
    private lateinit var etTanggal: EditText
    private lateinit var etWaktu: EditText
    private lateinit var etIDPelanggan: EditText
    private lateinit var etIDPengguna: EditText
    private lateinit var etKodeMeja: EditText
    private lateinit var etTotal: EditText
    private lateinit var etNamaPelanggan: EditText
    private lateinit var etTotalDiskon: EditText
    private lateinit var etIDPromosi: EditText
    private lateinit var spinnerShift: Spinner
    private lateinit var spinnerMetodeBayar: Spinner
    private lateinit var spinnerStatusPesanan: Spinner
    private lateinit var btnAddTransaksi: Button
    private lateinit var DBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaksi)

        etIDTransaksi = findViewById(R.id.et_idTransaksi)
        etTanggal = findViewById(R.id.et_tanggal)
        etWaktu = findViewById(R.id.et_waktu)
        etIDPelanggan = findViewById(R.id.et_idpelanggan)
        etIDPengguna = findViewById(R.id.et_idpengguna)
        etNamaPelanggan = findViewById(R.id.et_namapelanggan)
        etTotal = findViewById(R.id.et_total)
        etTotalDiskon = findViewById(R.id.et_totaldiskon)
        etIDPromosi = findViewById(R.id.et_idpromosi)
        etKodeMeja = findViewById(R.id.et_kodemeja)
        spinnerShift = findViewById(R.id.spinner_shift)
        spinnerStatusPesanan = findViewById(R.id.spinner_statuspesanan)
        spinnerMetodeBayar = findViewById(R.id.spinner_metodebayar)
        btnAddTransaksi = findViewById(R.id.btn_addTransaksi)

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        etTanggal.setText(currentDate)

        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        etWaktu.setText(currentTime)

        DBHelper = DBHelper(this)
        setupSpinner()
        btnAddTransaksi.setOnClickListener(this)
    }

    private fun setupSpinner() {
        // spinner shift
        val shiftOptions = arrayOf(1, 2)
        val shiftAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, shiftOptions)
        spinnerShift.adapter = shiftAdapter

        // spinner status pesanan
        val statusPesananOptions = arrayOf("Baru", "Disajikan", "Diproses", "Selesai")
        val statusPesananAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusPesananOptions)
        spinnerStatusPesanan.adapter = statusPesananAdapter

        // spinner metode bayar
        val metodeBayarOptions = arrayOf("Cash", "Kartu Debit", "Kartu Kredit", "QRIS")
        val metodeBayarAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, metodeBayarOptions)
        spinnerMetodeBayar.adapter = metodeBayarAdapter


    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_addTransaksi) {
                val db = DBHelper(this)

                val idTransaksi = etIDTransaksi.text.toString()
                val tanggal = etTanggal.text.toString()
                val waktu = etWaktu.text.toString()
                val idPengguna = etIDPengguna.text.toString()
                val idPelanggan = etIDPelanggan.text.toString()
                val kodeMeja = etKodeMeja.text.toString()
                val total = etTotal.text.toString().toInt()
                val totalDiskon = etTotalDiskon.text.toString().toInt()
                val idPromosi = etIDPromosi.text.toString()
                val namaPelanggan = etNamaPelanggan.text.toString()
                val statusPesanan = spinnerStatusPesanan.selectedItem.toString()
                val shift = spinnerShift.selectedItem.toString().toInt()
                val metodeBayar = spinnerMetodeBayar.selectedItem.toString()

                if (idPengguna.isNotEmpty() && idPelanggan.isNotEmpty() && kodeMeja.isNotEmpty() && total != null
                    && totalDiskon != null) {
                    db.addTransaksi(idTransaksi, tanggal, waktu, shift, idPengguna, idPelanggan, statusPesanan, kodeMeja, namaPelanggan, total, metodeBayar, totalDiskon, idPromosi)
                    finish()
                } else {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}