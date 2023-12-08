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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DBHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 5
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

        // transaksi
        private val TABLE_TRANSAKSI = "Transaksi"
        private val KEY_TRANSAKSI_IDTRANSAKSI = "idTransaksi"
        private val KEY_TRANSAKSI_TANGGAL = "tanggal"
        private val KEY_TRANSAKSI_WAKTU = "waktu"
        private val KEY_TRANSAKSI_SHIFT = "shift"
        private val KEY_TRANSAKSI_IDPENGGUNA = "idPengguna"
        private val KEY_TRANSAKSI_IDPELANGGAN = "idPelanggan"
        private val KEY_TRANSAKSI_STATUS = "status"
        private val KEY_TRANSAKSI_KODEMEJA = "kodeMeja"
        private val KEY_TRANSAKSI_NAMAPELANGGAN = "namaPelanggan"
        private val KEY_TRANSAKSI_TOTAL = "total"
        private val KEY_TRANSAKSI_METODEBAYAR = "metodePembayaran"
        private val KEY_TRANSAKSI_TOTALDISKON = "totalDiskon"
        private val KEY_TRANSAKSI_IDPROMOSI = "idPromosi"

        //warung
        private val TABLE_WARUNG = "Warung"
        private val KEY_WARUNG_IDWARUNG = "idwarung"
        private val KEY_WARUNG_NAMA = "namawarung"
        private val KEY_WARUNG_LOGO = "logo"
        private val KEY_WARUNG_GAMBAR = "gambar"

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

        // transaksi
        val queryTransaksi = ("CREATE TABLE " + TABLE_TRANSAKSI + " ("
                        + KEY_TRANSAKSI_IDTRANSAKSI + " TEXT PRIMARY KEY , "
                        + KEY_TRANSAKSI_TANGGAL + " TEXT, "
                        + KEY_TRANSAKSI_WAKTU + " TEXT, "
                        + KEY_TRANSAKSI_SHIFT + " INTEGER, "
                        + KEY_TRANSAKSI_IDPENGGUNA + " INTEGER, "
                        + KEY_TRANSAKSI_IDPELANGGAN + " INTEGER, "
                        + KEY_TRANSAKSI_STATUS + " TEXT, "
                        + KEY_TRANSAKSI_KODEMEJA + " TEXT, "
                        + KEY_TRANSAKSI_NAMAPELANGGAN + " TEXT, "
                        + KEY_TRANSAKSI_TOTAL + " INTEGER, "
                        + KEY_TRANSAKSI_METODEBAYAR + " TEXT, "
                        + KEY_TRANSAKSI_TOTALDISKON + " INTEGER, "
                        + KEY_TRANSAKSI_IDPROMOSI + " INTEGER" + ")")

        db.execSQL(queryTransaksi)

        // warung
        val queryWarung = ("CREATE TABLE " + TABLE_WARUNG + " ("
                + KEY_WARUNG_IDWARUNG + " TEXT PRIMARY KEY , "
                + KEY_WARUNG_NAMA + " TEXT, "
                + KEY_WARUNG_LOGO + " BLOB, "
                + KEY_WARUNG_GAMBAR + " BLOB," + ")")

        db.execSQL(queryWarung)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGGUNA)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKTVPENGGUNA)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI)
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

    fun getHashedPassword(username: String): String? {
        val db = this.readableDatabase
        val query = "SELECT $KEY_PENGGUNA_PASSWORD FROM $TABLE_PENGGUNA WHERE $KEY_PENGGUNA_USERNAME = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(username))
        var hashedPassword: String? = null

        if (cursor.moveToFirst()) {
            hashedPassword = cursor.getString(cursor.getColumnIndex(KEY_PENGGUNA_PASSWORD))
        }

        cursor.close()
        db.close()
        return hashedPassword
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
        val query = "SELECT * FROM ${TABLE_ROLE}"
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

    fun updateRole(idRole: String, roleName: String, status: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ROLE_ROLE, roleName)
        contentValues.put(KEY_ROLE_STATUS, status)

        try {
            db.beginTransaction()
            val result = db.update(TABLE_ROLE, contentValues, "$KEY_ROLE_IDROLE = ?", arrayOf(idRole))

            if (result > 0) {
                db.setTransactionSuccessful()
                Toast.makeText(context, "Berhasil mengedit role", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Gagal mengedit role", Toast.LENGTH_SHORT).show()
            }

            return result != -1
        } catch (e: Exception) {
            Toast.makeText(context, "Gagal mengupdate pengguna", Toast.LENGTH_SHORT).show()
            return false
        } finally {
            db.endTransaction()
        }
    }

    fun deleteRole(idRole: Int) {
        // logic delete role
    }

    fun addTransaksi(
        idTransaksi: String,
        tanggal: String,
        waktu: String,
        shift: Int,
        idPengguna: String,
        idPelanggan: String?,
        status: String,
        kodeMeja: String,
        namaPelanggan: String?,
        total: Int,
        metodePembayaran: String,
        totalDiskon: Int,
        idPromosi: String?
    ) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(KEY_TRANSAKSI_IDTRANSAKSI, idTransaksi)
            put(KEY_TRANSAKSI_TANGGAL, tanggal)
            put(KEY_TRANSAKSI_WAKTU, waktu)
            put(KEY_TRANSAKSI_SHIFT, shift)
            put(KEY_TRANSAKSI_IDPENGGUNA, idPengguna)
            put(KEY_TRANSAKSI_IDPELANGGAN, idPelanggan)
            put(KEY_TRANSAKSI_STATUS, status)
            put(KEY_TRANSAKSI_KODEMEJA, kodeMeja)
            put(KEY_TRANSAKSI_NAMAPELANGGAN, namaPelanggan)
            put(KEY_TRANSAKSI_TOTAL, total)
            put(KEY_TRANSAKSI_METODEBAYAR, metodePembayaran)
            put(KEY_TRANSAKSI_TOTALDISKON, totalDiskon)
            put(KEY_TRANSAKSI_IDPROMOSI, idPromosi)
        }

        val result = db.insert(TABLE_TRANSAKSI, null, values)

        if (result != -1L) {
            Toast.makeText(context, "Berhasil menambahkan transaksi", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal menambahkan transaksi", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    fun getAllTransaksi(): Cursor {
        val query = "SELECT * FROM ${TABLE_TRANSAKSI}"
        val db = this.readableDatabase
        return db.rawQuery(query, null)
    }

    fun getTotalHariIni(): Int {
        var totalHariIni = 0

        val db = this.readableDatabase

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = sdf.format(Date())

        val query = "SELECT SUM($KEY_TRANSAKSI_TOTAL) AS total FROM $TABLE_TRANSAKSI WHERE $KEY_TRANSAKSI_TANGGAL = ?"

        val cursor: Cursor = db.rawQuery(query, arrayOf(todayDate))
        if (cursor.moveToFirst()) {
            totalHariIni = cursor.getInt(cursor.getColumnIndex("total"))
        }
        cursor.close()

        return totalHariIni
    }

    fun getTotalBulanIni(): Int {
        var totalBulanIni = 0

        val db = this.readableDatabase

        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val thisMonth = sdf.format(Date())

        val query = "SELECT SUM($KEY_TRANSAKSI_TOTAL) AS total FROM $TABLE_TRANSAKSI WHERE SUBSTR($KEY_TRANSAKSI_TANGGAL, 1, 7) = ?"

        val cursor: Cursor = db.rawQuery(query, arrayOf(thisMonth))
        if (cursor.moveToFirst()) {
            totalBulanIni = cursor.getInt(cursor.getColumnIndex("total"))
        }
        cursor.close()

        return totalBulanIni
    }

    fun getJumlahTransaksi(): Int {
        var jumlahTransaksi = 0

        val db = this.readableDatabase

        val query = "SELECT COUNT($KEY_TRANSAKSI_IDTRANSAKSI) AS jumlah FROM $TABLE_TRANSAKSI"

        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            jumlahTransaksi = cursor.getInt(cursor.getColumnIndex("jumlah"))
        }
        cursor.close()

        return jumlahTransaksi
    }

    fun addWarung(idwarung: String, namawarung: String, logo: Bitmap, gambar: Bitmap) {
        val objectByteOutputStream = ByteArrayOutputStream()
        logo.compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream)
        gambar.compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream)
        val imageInBytes = objectByteOutputStream.toByteArray()

        val values = ContentValues()

        values.put(KEY_WARUNG_IDWARUNG, idwarung)
        values.put(KEY_WARUNG_NAMA, namawarung)
        values.put(KEY_WARUNG_LOGO, imageInBytes)
        values.put(KEY_WARUNG_GAMBAR, imageInBytes)

        val db = this.writableDatabase
        val result = db.insert(TABLE_WARUNG, null, values)

        if (result != -1L) {
            Toast.makeText(context, "Berhasil menambah warung", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal menambah warung", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

}