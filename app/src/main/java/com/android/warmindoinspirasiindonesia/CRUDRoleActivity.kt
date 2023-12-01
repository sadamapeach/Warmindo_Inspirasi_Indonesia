package com.android.warmindoinspirasiindonesia

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudrole)

        recyclerViewRoles = findViewById(R.id.recyclerViewRoles)
        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this)

        idRole = ArrayList()
        role = ArrayList()
        status = ArrayList()

        storeDataInArrays()

        roleListAdapter = RoleListAdapter(this, idRole, role, status)
        recyclerViewRoles.adapter = roleListAdapter
        recyclerViewRoles.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
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
                idRole.add(cursor.getString(0))
                role.add(cursor.getString(1))
                status.add(cursor.getString(2))
            }
        }
    }
}
