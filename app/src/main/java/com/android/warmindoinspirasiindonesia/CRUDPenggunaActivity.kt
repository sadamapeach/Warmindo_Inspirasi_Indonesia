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

class CRUDPenggunaActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private var idPengguna = ArrayList<String>()
    private var namaPengguna = ArrayList<String>()
    private var role = ArrayList<String>()
    private var status = ArrayList<String>()
    private var foto = ArrayList<String>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudpengguna)

        recyclerView = findViewById(R.id.recyclerView)
        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this)

        idPengguna = ArrayList()
        namaPengguna = ArrayList()
        role = ArrayList()
        status = ArrayList()
        foto = ArrayList()

        storeDataInArrays()

//        customAdapter = CustomAdapter(this, idPengguna, namaPengguna, role, status, foto)
//        recyclerView.adapter = customAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                val addPenggunaIntent = Intent(this, AddPenggunaActivity::class.java)
                startActivity(addPenggunaIntent)
            }
        }
    }

    private fun storeDataInArrays() {
        val db = DBHelper(this)
        val cursor: Cursor = db.getAllPengguna()

        if (cursor.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idPengguna.add(cursor.getString(0))
                namaPengguna.add(cursor.getString(3))
                role.add(cursor.getString(7))
                status.add(cursor.getString(4))
            }
        }
    }
}
