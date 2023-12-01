package com.android.warmindoinspirasiindonesia
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Spinner
import android.widget.Toast
import com.android.warmindoinspirasiindonesia.DBHelper

class EditRoleActivity : AppCompatActivity() {

    private lateinit var etRoleName: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSave: Button
    private lateinit var DBHelper: DBHelper
    private var roleId: Int = 0
    private var roleName: String = ""
    private var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_role)

        etRoleName = findViewById(R.id.et_role_name)
        spinnerStatus = findViewById(R.id.spinner_status)
        btnSave = findViewById(R.id.btn_save)
        DBHelper = DBHelper(this)

        btnSave.setOnClickListener {
            saveRoleChanges()
        }

        getAndSetIntentData()
        setupSpinner()
    }

    private fun setupSpinner() {
        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinnerStatus.adapter = statusAdapter

        val statusPosition = statusOptions.indexOfFirst { it == status }

        if (statusPosition != -1) {
            spinnerStatus.setSelection(statusPosition)
        }
    }

    private fun saveRoleChanges() {
        val updatedRoleName = etRoleName.text.toString()
        val updatedStatus = spinnerStatus.selectedItem.toString()

        val success = DBHelper.updateRole(roleId, updatedRoleName, updatedStatus)

        if (success) {
            Toast.makeText(this, "Role berhasil diperbarui", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Gagal memperbarui role", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("role") && intent.hasExtra("status")) {
            roleId = intent.getIntExtra("id", 0)
            roleName = intent.getStringExtra("role") ?: ""
            status = intent.getStringExtra("status") ?: ""

            etRoleName.setText(roleName)
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        }
    }
}
