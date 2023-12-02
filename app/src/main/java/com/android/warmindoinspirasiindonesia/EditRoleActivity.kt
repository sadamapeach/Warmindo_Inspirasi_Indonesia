package com.android.warmindoinspirasiindonesia
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Spinner
import android.widget.Toast
import com.android.warmindoinspirasiindonesia.DBHelper

class EditRoleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etRoleName: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSave: Button
    private lateinit var DBHelper: DBHelper
    private var roleId: String = ""
    private var roleName: String = ""
    private var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_role)

        etRoleName = findViewById(R.id.et_role_name)
        spinnerStatus = findViewById(R.id.spinner_status)
        btnSave = findViewById(R.id.btn_save)
        DBHelper = DBHelper(this)

//        btnSave.setOnClickListener {
//            saveRoleChanges()
//        }
        btnSave.setOnClickListener(this)

        getAndSetIntentData()
        setupSpinner()
    }

    private fun setupSpinner() {
        val status = intent.getStringExtra("status") ?: ""

        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val statusPosition = statusOptions.indexOfFirst { it == status }
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinnerStatus.adapter = statusAdapter

        if (statusPosition != -1) {
            spinnerStatus.setSelection(statusPosition)
        }
    }

    private fun saveRoleChanges() {
        val updatedRoleName = etRoleName.text.toString()
        val updatedStatus = spinnerStatus.selectedItem.toString()

        DBHelper.updateRole(roleId, updatedRoleName, updatedStatus)

        finish()
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("idRole") && intent.hasExtra("role") && intent.hasExtra("status")) {
            roleId = intent.getStringExtra("idRole") ?: ""
            roleName = intent.getStringExtra("role") ?: ""
            status = intent.getStringExtra("status") ?: ""

            etRoleName.setText(roleName)
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_save) {
                val updatedRoleName = etRoleName.text.toString()
                val updatedStatus = spinnerStatus.selectedItem.toString()

                DBHelper.updateRole(roleId, updatedRoleName, updatedStatus)
                finish()
            }
        }
    }
}
