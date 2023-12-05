package com.android.warmindoinspirasiindonesia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class AddRoleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etIdRole: EditText
    private lateinit var etRole: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnAdd: Button
    private lateinit var DBHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_role)

        etIdRole = findViewById(R.id.et_idRole)
        etRole = findViewById(R.id.et_role)
        spinnerStatus = findViewById(R.id.spinner_status)
        btnAdd = findViewById(R.id.btn_add)

        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinnerStatus.adapter = statusAdapter
        DBHelper = DBHelper(this)

        setupSpinner()

        btnAdd.setOnClickListener(this)
    }

    private fun setupSpinner() {
        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter
    }


    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                val db = DBHelper(this)

                val idRole = etIdRole.text.toString().toIntOrNull()
                val role = etRole.text.toString()
                val status = spinnerStatus.selectedItem.toString()

                if (idRole != null && role.isNotEmpty() && status.isNotEmpty()) {
                    val defaultStatus = "Tidak Aktif"
                    db.addRole(idRole, role, status)
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
