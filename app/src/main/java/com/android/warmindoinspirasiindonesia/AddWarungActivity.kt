//package com.android.warmindoinspirasiindonesia
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.Size
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import java.io.IOException
//
//class AddWarungActivity : AppCompatActivity(), View.OnClickListener {
//    private lateinit var etIdWarung: EditText
//    private lateinit var etNamaWarung: EditText
//    private lateinit var btnAdd: Button
//    private lateinit var btnSelectImageLogo: Button
//    private lateinit var btnSelectImageGambar: Button
//    private lateinit var ivLogo: ImageView
//    private lateinit var ivGambar: ImageView
//    private var thumbnail: Bitmap? = null
//    private val PICK_IMAGE_REQUEST = 1
//    private val PICK_IMAGE_LOGO_REQUEST = 2
//    private val PICK_IMAGE_GAMBAR_REQUEST = 3
//    private lateinit var DBHelper: DBHelper
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_warung)
//
//        etIdWarung = findViewById(R.id.et_idwarung)
//        etNamaWarung = findViewById(R.id.et_namawarung)
//        btnAdd = findViewById(R.id.btn_add)
//        btnSelectImageLogo = findViewById(R.id.btn_selectImageLogo)
//        btnSelectImageGambar = findViewById(R.id.btn_selectImageGambar)
//        ivLogo = findViewById(R.id.iv_logo)
//        ivGambar = findViewById(R.id.iv_gambar)
//
//        DBHelper = DBHelper(this)
//
//        btnAdd.setOnClickListener(this)
//        btnSelectImageLogo.setOnClickListener(this)
//
//        btnAdd.setOnClickListener(this)
//        btnSelectImageGambar.setOnClickListener(this)
//    }
//
////    override fun onClick(view: View?) {
////        if (view != null) {
////            if (view.id == R.id.btn_selectImageLogo) {
////                openGallery(PICK_IMAGE_LOGO_REQUEST)
////            } else if (view.id == R.id.btn_selectImageGambar) {
////                openGallery(PICK_IMAGE_GAMBAR_REQUEST)
////            } else if (view.id == R.id.btn_add) {
////                tambahkanWarungKeDatabase()
////            }
////        }
////    }
//
////    private fun tambahkanWarungKeDatabase() {
////        val db = DBHelper(this)
////
////        val idWarung = etIdWarung.text.toString()
////        val namaWarung = etNamaWarung.text.toString()
////
////        if (idWarung.isNotEmpty() && namaWarung.isNotEmpty() && thumbnail != null) {
////
////            val defaultImageResource = R.drawable.pic_add
////            val safeThumbnail = thumbnail ?: BitmapFactory.decodeResource(resources, defaultImageResource)
////
////
////            db.addWarung(idWarung.toInt(), namaWarung, safeThumbnail, safeThumbnail)
////            finish()
////        } else {
////
////            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
////        }
////    }
//
//    private fun openGallery(requestCode: Int) {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, requestCode)
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK && data != null) {
//            if (requestCode == PICK_IMAGE_LOGO_REQUEST || requestCode == PICK_IMAGE_GAMBAR_REQUEST) {
//                handleImageSelection(data, requestCode)
//            }
//        }
//    }
//
//    private fun handleImageSelection(data: Intent, requestCode: Int) {
//        val imageUri = data.data
//
//        try {
//            thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                contentResolver.loadThumbnail(imageUri!!, Size(640, 480), null)
//            } else {
//                val inputStream = contentResolver.openInputStream(imageUri!!)
//                BitmapFactory.decodeStream(inputStream)
//            }
//            val imageView = findViewById<ImageView>(if (requestCode == PICK_IMAGE_LOGO_REQUEST) R.id.iv_logo else R.id.iv_gambar)
//            imageView.setImageBitmap(thumbnail)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//}