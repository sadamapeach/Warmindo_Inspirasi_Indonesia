package com.android.warmindoinspirasiindonesia

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CRUDRoleActivity: AppCompatActivity(), View.OnClickListener {

    private var idRole = ArrayList<String>()
    private var role = ArrayList<String>()
    private var status = ArrayList<String>()
    private lateinit var btnAdd: Button
    private lateinit var recyclerViewRoles: RecyclerView
    private lateinit var roleListAdapter: RoleListAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudrole)

        btnAdd = findViewById(R.id.btn_add)
        recyclerViewRoles = findViewById(R.id.recyclerViewRoles)

        btnAdd.setOnClickListener(this)

        idRole = ArrayList()
        role = ArrayList()
        status = ArrayList()

        storeDataInArrays()

        roleListAdapter = RoleListAdapter(this)
        recyclerViewRoles.layoutManager = LinearLayoutManager(this)
        recyclerViewRoles.adapter = roleListAdapter

    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                // Aksi saat klik tombol Add
                val addRoleIntent = Intent(this, AddRoleActivity::class.java)
                startActivity(addRoleIntent)
            }
        }
    }

    private fun storeDataInArrays() {
        val db = DBHelper(this)
        val cursor: Cursor = db.getAllRoles2()

        if (cursor.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idRole.add(cursor.getInt(0).toString())
                role.add(cursor.getString(3))
                status.add(cursor.getString(4))
            }
        }
    }






}
