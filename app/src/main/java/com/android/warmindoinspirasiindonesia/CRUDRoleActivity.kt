package com.android.warmindoinspirasiindonesia

import RoleListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CRUDRoleActivity<Role> : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnAdd: Button
    private lateinit var recyclerViewRoles: RecyclerView
    private lateinit var roleListAdapter: RoleListAdapter

    private fun getRolesFromDatabase(): List<com.android.warmindoinspirasiindonesia.Role> {
        val dbHelper = DBHelper(this)
        return dbHelper.getAllRoles()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudrole)

        btnAdd = findViewById(R.id.btn_add)
        recyclerViewRoles = findViewById(R.id.recyclerViewRoles)

        btnAdd.setOnClickListener(this)

        roleListAdapter = RoleListAdapter(this)
        recyclerViewRoles.layoutManager = LinearLayoutManager(this)
        recyclerViewRoles.adapter = roleListAdapter

        // Ambil data dari database dan perbarui adapter
        updateAdapterData()
    }

    private fun updateAdapterData() {
        val dbHelper = DBHelper(this)
        val rolesFromDatabase = dbHelper.getAllRoles()
        roleListAdapter.updateData(rolesFromDatabase)
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
}
