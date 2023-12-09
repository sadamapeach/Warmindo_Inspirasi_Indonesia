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

class DataWarungActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var idWarung = ArrayList<String>()
    private var namaWarung = ArrayList<String>()
    private var logo = ArrayList<String>()
    private lateinit var warungAdapter: WarungAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_warung)

        recyclerView = findViewById(R.id.recyclerViewWarung)

        idWarung = ArrayList()
        namaWarung = ArrayList()
        logo = ArrayList()

        storeDataInArrays()

        warungAdapter = WarungAdapter(this, idWarung, namaWarung, logo)
        recyclerView.adapter = warungAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun storeDataInArrays() {
        val db = DBHelper(this)
        val cursor: Cursor = db.getAllWarung()

        if (cursor.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idWarung.add(cursor.getString(0))
                namaWarung.add(cursor.getString(1))
            }
        }
    }
}
