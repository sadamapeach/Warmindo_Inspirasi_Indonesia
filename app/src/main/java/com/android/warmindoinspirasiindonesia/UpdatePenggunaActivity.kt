package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.io.IOException

class UpdatePenggunaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivFoto: ImageView
    private lateinit var etNamaPengguna: TextView
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSave: Button
    private lateinit var DBHelper: DBHelper
    private lateinit var spinnerRole: Spinner
    private var thumbnail: Bitmap? = null
    private val PICK_IMAGE_REQUEST = 1
    private var userId: String = ""
    private var id: String = ""
    private var nama: String = ""
    private var status: String = ""
    private var foto: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pengguna)

        ivFoto = findViewById(R.id.iv_foto)
        etNamaPengguna = findViewById(R.id.et_namaPengguna)
        spinnerStatus = findViewById(R.id.spinner_status)
        spinnerRole = findViewById(R.id.spinner_role)
        btnSave = findViewById(R.id.btn_save)
        DBHelper = DBHelper(this)

        // ambil ID pengguna dari intent
        userId = intent.getStringExtra("id") ?: ""

        btnSave.setOnClickListener(this)
        ivFoto.setOnClickListener(this)

        getAndSetIntentData()
        setupSpinner()
    }
    private fun setupSpinner() {
        val roles = DBHelper.getAllRoles()
        val rolesOptions = roles.map { it.role }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rolesOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapter

        val role = intent.getStringExtra("role") ?: ""

        val rolePosition = rolesOptions.indexOfFirst { it == role }

        if (rolePosition != -1) {
            spinnerRole.setSelection(rolePosition)
        }

        // Ambil status dari intent
        val status = intent.getStringExtra("status") ?: ""

        val statusOptions = arrayOf("Aktif", "Tidak Aktif")
        val statusPosition = statusOptions.indexOfFirst { it == status }
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
        spinnerStatus.adapter = statusAdapter

        if (statusPosition != -1) {
            spinnerStatus.setSelection(statusPosition)
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.iv_foto) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } else if (view.id == R.id.btn_save) {
                val updatedName = etNamaPengguna.text.toString()
                val updatedStatus = spinnerStatus.selectedItem.toString()
                val updatedRole = spinnerRole.selectedItem.toString()
                val selectedRole = DBHelper.getAllRoles().find { it.role == updatedRole }
                val idRole = selectedRole!!.idRole
                val defaultImageResource = R.drawable.profpic
                val safeThumbnail =
                    thumbnail ?: BitmapFactory.decodeResource(resources, defaultImageResource)
                //val updatedFoto: ByteArray? = // Ambil data foto yang baru dari antarmuka, misalnya dengan metode getImageData()

                val success = DBHelper.updatePengguna(userId, updatedName, updatedStatus, idRole, safeThumbnail)

                if (success) {
                    Toast.makeText(this, "Pengguna berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    // Tambahkan tindakan sesuai kebutuhan, misalnya kembali ke aktivitas sebelumnya
                    finish()
                } else {
                    Toast.makeText(this, "Gagal memperbarui pengguna", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("nama")
        ) {
            // get data from intent
            id = intent.getStringExtra("id") ?: ""
            nama = intent.getStringExtra("nama") ?: ""
            status = intent.getStringExtra("status") ?: ""
            //role = intent.getStringExtra("role") ?: ""
            foto = intent.getByteArrayExtra("foto")

            // set data
            etNamaPengguna.text = nama
            spinnerStatus.setSelection(if (status == "Aktif") 1 else 0)

            if (foto != null && foto!!.isNotEmpty()) {
                val bitmap: Bitmap = BitmapFactory.decodeByteArray(foto, 0, foto!!.size)
                ivFoto.setImageBitmap(bitmap)
            }
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        }
    }
}