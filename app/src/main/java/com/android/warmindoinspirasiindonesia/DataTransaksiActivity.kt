package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataTransaksiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private var idTransaksi = ArrayList<String>()
    private var tanggal = ArrayList<String>()
    private var total = ArrayList<String>()
    private lateinit var transaksiAdapter: TransaksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_transaksi)

        recyclerView = findViewById(R.id.recyclerViewTransaksi)

        idTransaksi = ArrayList()
        tanggal = ArrayList()
        total = ArrayList()

        storeDataInArrays()

        transaksiAdapter = TransaksiAdapter(this, idTransaksi, tanggal, total)
        recyclerView.adapter = transaksiAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(view: View?) {
//        if (view != null) {
//            if (view.id == R.id.btn_add) {
//                val addPenggunaIntent = Intent(this, AddPenggunaActivity::class.java)
//                startActivity(addPenggunaIntent)
//            }
//        }
    }

    private fun storeDataInArrays() {
        val db = DBHelper(this)
        val cursor: Cursor = db.getAllTransaksi()

        if (cursor.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idTransaksi.add(cursor.getString(0))
                tanggal.add(cursor.getString(1))
                total.add(cursor.getString(9))
            }
        }
    }
}
