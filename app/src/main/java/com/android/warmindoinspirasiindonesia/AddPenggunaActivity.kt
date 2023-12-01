package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import android.os.Build
import android.util.Size
import com.android.warmindoinspirasiindonesia.databinding.ActivityAddPenggunaBinding
import java.io.IOException

class AddPenggunaActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etNama: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnAdd: Button
    private lateinit var btnSelectImage: Button
    private lateinit var ivFoto: ImageView
    private var thumbnail: Bitmap? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var DBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pengguna)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etNama = findViewById(R.id.et_nama)
        ivFoto = findViewById(R.id.iv_foto)
        spinnerRole = findViewById(R.id.spinner_role)
        spinnerStatus = findViewById(R.id.spinner_status)
        btnAdd = findViewById(R.id.btn_add)
        btnSelectImage = findViewById(R.id.btn_selectImage)

        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)

        spinnerStatus.adapter = statusAdapter
        DBHelper = DBHelper(this)

        setupSpinner()

        btnAdd.setOnClickListener(this)
        btnSelectImage.setOnClickListener(this)
    }

    private fun setupSpinner() {
        val roles = DBHelper.getAllRoles()
        val roleNames = roles.map { it.role }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roleNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data

            try {
                thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentResolver.loadThumbnail(imageUri!!, Size(640, 480), null)
                } else {
                    val inputStream = contentResolver.openInputStream(imageUri!!)
                    BitmapFactory.decodeStream(inputStream)
                }
                val imageView = findViewById<ImageView>(R.id.iv_foto)
                imageView.setImageBitmap(thumbnail)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_selectImage) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } else if (view.id == R.id.btn_add) {
                val db = DBHelper(this)

                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val nama = etNama.text.toString()
                val status = spinnerStatus.selectedItem.toString()

                val selectedRoleName = spinnerRole.selectedItem.toString()
                val selectedRole = DBHelper.getAllRoles().find { it.role == selectedRoleName }

                if (username.isNotEmpty() && password.isNotEmpty() && nama.isNotEmpty() && selectedRole != null && status.isNotEmpty()) {
                    val role = selectedRole.idRole
                    val defaultImageResource = R.drawable.profpic
                    val safeThumbnail =
                        thumbnail ?: BitmapFactory.decodeResource(resources, defaultImageResource)

                    db.addPengguna(username, password, nama, role, status, safeThumbnail)

                    etUsername.text.clear()
                    etPassword.text.clear()
                    etNama.text.clear()
                } else {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}