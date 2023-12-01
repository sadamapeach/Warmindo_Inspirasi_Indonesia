package com.android.warmindoinspirasiindonesia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DBHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Warmindo"

        // users
        private val TABLE_USERS = "users"
        private val KEY_USERS_ID = "id"
        private val KEY_USERS_USERNAME = "username"
        private val KEY_USERS_PASSWORD = "password"

        // role
        private val TABLE_ROLE = "Role"
        private val KEY_ROLE_IDROLE = "idRole"
        private val KEY_ROLE_ROLE = "role"
        private val KEY_ROLE_STATUS = "status"

        // pengguna
        private val TABLE_PENGGUNA = "Pengguna"
        private val KEY_PENGGUNA_IDPENGGUNA = "idPengguna"
        private val KEY_PENGGUNA_USERNAME = "username"
        private val KEY_PENGGUNA_PASSWORD = "password"
        private val KEY_PENGGUNA_NAMA = "namaPengguna"
        private val KEY_PENGGUNA_IDROLE = "idRole"
        private val KEY_PENGGUNA_STATUS = "status"
        private val KEY_PENGGUNA_FOTO = "foto"

        // aktivitas pengguna
        private val TABLE_AKTVPENGGUNA = "AktivitasPengguna"
        private val KEY_AKTVPENGGUNA_IDAKTIVITAS = "idAktivitas"
        private val KEY_AKTVPENGGUNA_TANGGAL = "tanggal"
        private val KEY_AKTVPENGGUNA_WAKTU = "waktu"
        private val KEY_AKTVPENGGUNA_IDPENGGUNA = "idPengguna"
        private val KEY_AKTVPENGGUNA_AKTIVITAS = "aktivitas"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // users
        val queryUsers = ("CREATE TABLE " + TABLE_USERS + " ("
                + KEY_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERS_USERNAME + " TEXT," +
                KEY_USERS_PASSWORD + " TEXT" + ")")

        db.execSQL(queryUsers)

        // role
        val queryRole = ("CREATE TABLE " + TABLE_ROLE + " ("
                + KEY_ROLE_IDROLE + " INTEGER PRIMARY KEY, " +
                KEY_ROLE_ROLE + " TEXT," +
                KEY_ROLE_STATUS + " TEXT" + ")")

        db.execSQL(queryRole)

        // pengguna
        val queryPengguna = ("CREATE TABLE " + TABLE_PENGGUNA + " ("
                + KEY_PENGGUNA_IDPENGGUNA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_PENGGUNA_USERNAME + " TEXT, " +
                KEY_PENGGUNA_PASSWORD + " TEXT, " +
                KEY_PENGGUNA_NAMA + " TEXT, " +
                KEY_PENGGUNA_STATUS + " TEXT, " +
                KEY_PENGGUNA_FOTO + " BLOB, " +
                KEY_PENGGUNA_IDROLE + " INTEGER" + ")")

        db.execSQL(queryPengguna)
//
//        // aktivitas pengguna
//        val queryAktvPengguna = ("CREATE TABLE " + TABLE_AKTVPENGGUNA + " ("
//                + KEY_AKTVPENGGUNA_IDAKTIVITAS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                KEY_AKTVPENGGUNA_TANGGAL + " TEXT, " +
//                KEY_AKTVPENGGUNA_WAKTU + " TEXT, " +
//                KEY_AKTVPENGGUNA_IDPENGGUNA + " INTEGER, " +
//                KEY_AKTVPENGGUNA_AKTIVITAS + " TEXT, " +
//                "FOREIGN KEY(" + KEY_AKTVPENGGUNA_IDPENGGUNA + ") REFERENCES " + TABLE_PENGGUNA + "(" + KEY_PENGGUNA_IDPENGGUNA + ")")
//
//        db.execSQL(queryAktvPengguna)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGGUNA)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKTVPENGGUNA)
        onCreate(db)
    }

    fun addUser(username : String, password : String ){
        val values = ContentValues()

        values.put(KEY_PENGGUNA_USERNAME, username)
        values.put(KEY_PENGGUNA_PASSWORD, password)

        val db = this.writableDatabase

        db.beginTransaction()

        try {
            val result = db.insert(TABLE_PENGGUNA, null, values)

            if (result != -1L) {
                db.setTransactionSuccessful() // Commit transaksi jika berhasil
                Toast.makeText(context, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Registrasi gagal", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Tangani kesalahan jika ada
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun checkUsername(username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        val usernameExists = cursor.count > 0

        cursor.close()
        db.close()

        return usernameExists
    }

    fun checkCredential(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_USERNAME = ? AND $KEY_PENGGUNA_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        val userExists = cursor.count > 0

        cursor.close()
        db.close()

        return userExists
    }

    fun addRole(idRole : Int, role : String, status : String ){
        val values = ContentValues()

        values.put(KEY_ROLE_IDROLE, idRole)
        values.put(KEY_ROLE_ROLE, role)
        values.put(KEY_ROLE_STATUS, status)

        val db = this.writableDatabase
        val result = db.insert(TABLE_ROLE, null, values)

        if (result != -1L) {
            Toast.makeText(context, "Berhasil menambah role", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal menambah role", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    fun addAktivitasPengguna(tanggal: String, waktu: String, idPengguna: Int, aktivitas: String) {
        val values = ContentValues()

        values.put(KEY_AKTVPENGGUNA_TANGGAL, tanggal)
        values.put(KEY_AKTVPENGGUNA_WAKTU, waktu)
        values.put(KEY_AKTVPENGGUNA_IDPENGGUNA, idPengguna)
        values.put(KEY_AKTVPENGGUNA_AKTIVITAS, aktivitas)

        val db = this.writableDatabase

        val result = db.insert(TABLE_AKTVPENGGUNA, null, values)

        if (result != -1L) {
            db.setTransactionSuccessful()
            Toast.makeText(context, "Berhasil menambah aktivitas pengguna", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal menambah aktivitas pengguna", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

//    private fun saveImageToStorage(bitmap: Bitmap, context: Context): String {
//        val imageFileName = "JPEG_${System.currentTimeMillis()}_"
//        val resolver = context.contentResolver
//        val contentValues = ContentValues().apply {
//            put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
//            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//            }
//        }
//
//        val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//        try {
//            imageUri?.let {
//                resolver.openOutputStream(it)?.use { outputStream ->
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return imageUri?.toString() ?: ""
//    }

    fun addPengguna(username: String, password: String, nama: String, idRole: Int, status: String, foto: Bitmap) {
        val objectByteOutputStream = ByteArrayOutputStream()
        foto.compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream)
        val imageInBytes = objectByteOutputStream.toByteArray()

        val values = ContentValues()

        values.put(KEY_PENGGUNA_USERNAME, username)
        values.put(KEY_PENGGUNA_PASSWORD, password)
        values.put(KEY_PENGGUNA_NAMA, nama)
        values.put(KEY_PENGGUNA_IDROLE, idRole)
        values.put(KEY_PENGGUNA_FOTO, imageInBytes)
        values.put(KEY_PENGGUNA_STATUS, status)

        val db = this.writableDatabase
        val result = db.insert(TABLE_PENGGUNA, null, values)

        if (result != -1L) {
            Toast.makeText(context, "Berhasil menambah pengguna", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal menambah pengguna", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    fun getAllRoles(): List<Roles> {
        val roleList = mutableListOf<Roles>()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ROLE"
        val cursor = db.rawQuery(query, null)

        cursor.use {
            while (it.moveToNext()) {
                val roleId = it.getInt(it.getColumnIndex(KEY_ROLE_IDROLE))
                val roleName = it.getString(it.getColumnIndex(KEY_ROLE_ROLE))
                val roleStatus = it.getString(it.getColumnIndex(KEY_ROLE_STATUS))

                val role = Roles(roleId, roleName, roleStatus)
                roleList.add(role)
            }
        }
        return roleList
    }

    fun getAllRoles2(): Cursor {
        val query = "SELECT * FROM $TABLE_ROLE"
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    fun getAllPengguna(): Cursor {
        val query = "SELECT ${TABLE_PENGGUNA}.*, ${TABLE_ROLE}.role FROM ${TABLE_PENGGUNA} INNER JOIN ${TABLE_ROLE} ON ${TABLE_PENGGUNA}.idRole = ${TABLE_ROLE}.idRole"
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    fun getPenggunaByID(idPengguna: String): Pengguna? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_IDPENGGUNA = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idPengguna))

        var userData: Pengguna? = null

        try {
            if (cursor.moveToFirst()) {
                userData = Pengguna(
                    idPengguna = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_IDPENGGUNA)),
                    username = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_USERNAME)),
                    password = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_PASSWORD)),
                    nama = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_NAMA)),
                    role = cursor.getInt(cursor.getColumnIndex(KEY_PENGGUNA_IDROLE)),
                    status = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_STATUS)),
                    foto = getFotoPengguna(idPengguna)
                )
            }
        } finally {
            cursor.close()
        }

        return userData
    }


    fun getAllFotoPengguna(userId: String): ByteArray? {
        val db = this.readableDatabase
        var imageData: ByteArray? = null

        val query = "SELECT foto FROM $TABLE_PENGGUNA WHERE idPengguna = ?"
        val cursor = db.rawQuery(query, arrayOf(userId))

        cursor?.use {
            if (it.moveToFirst()) {
                imageData = it.getBlob(it.getColumnIndex("foto"))
            }
        }
        return imageData
    }

//    fun getFotoPengguna(idPengguna: String): ByteArray? {
//        val db = this.readableDatabase
//        val query = "SELECT $KEY_PENGGUNA_FOTO FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_IDPENGGUNA = $idPengguna"
//        val cursor: Cursor = db.rawQuery(query, null)
//        var foto: ByteArray? = null
//
//        if (cursor.moveToFirst()) {
//            foto = cursor.getBlob(cursor.getColumnIndex(KEY_PENGGUNA_FOTO))
//        }
//
//        cursor.close()
//        db.close()
//
//        return foto
//    }
    fun getFotoPengguna(idPengguna: String): ByteArray? {
        val db = this.readableDatabase
        val query = "SELECT $KEY_PENGGUNA_FOTO FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_IDPENGGUNA = ?"
        val cursor: Cursor? = db.rawQuery(query, arrayOf(idPengguna))

        var foto: ByteArray? = null

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                foto = cursor.getBlob(cursor.getColumnIndex(KEY_PENGGUNA_FOTO))
            }
            cursor.close()
        }

        return foto
    }
    fun updatePengguna(id: String, nama: String, status: String, idRole: Int, foto: Bitmap): Boolean {
        val objectByteOutputStream = ByteArrayOutputStream()
        foto.compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream)
        val imageInBytes = objectByteOutputStream.toByteArray()

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_PENGGUNA_NAMA, nama)
        contentValues.put(KEY_PENGGUNA_STATUS, status)
        contentValues.put(KEY_PENGGUNA_IDROLE, idRole)
        contentValues.put(KEY_PENGGUNA_FOTO, imageInBytes)

        try {
            db.beginTransaction()

            val result = db.update(TABLE_PENGGUNA, contentValues, "$KEY_PENGGUNA_IDPENGGUNA = ?", arrayOf(id))

            if (result > 0) {
                db.setTransactionSuccessful()
                Toast.makeText(context, "Berhasil mengedit pengguna", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Gagal mengedit pengguna", Toast.LENGTH_SHORT).show()
            }

            return result != -1
        } catch (e: Exception) {
            Toast.makeText(context, "Gagal mengupdate pengguna", Toast.LENGTH_SHORT).show()
            return false
        } finally {
            db.endTransaction()
        }
    }


    fun deletePengguna(id: String) {
        val db = this.writableDatabase

        try {
            db.beginTransaction()

            val result = db.delete(TABLE_PENGGUNA, "idPengguna=?", arrayOf(id))

            if (result > 0) {
                db.setTransactionSuccessful()
                Toast.makeText(context, "Berhasil menghapus pengguna", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Gagal menghapus pengguna", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun updateRole(idRole: Int, roleName: String, status: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ROLE_ROLE, roleName)
        contentValues.put(KEY_ROLE_STATUS, status)

        val result = db.update(TABLE_ROLE, contentValues, "$KEY_ROLE_IDROLE = ?", arrayOf(idRole.toString()))

        return result != -1
    }

    fun deleteRole(idRole: Int) {
        // logic delete role
    }

}