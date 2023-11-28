package com.android.warmindoinspirasiindonesia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class PenggunaDBHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Warmindo"
        private val TABLE_NAME = "Pengguna"
        private val KEY_IDPENGGUNA = "idpengguna"
        private val KEY_USERNAME = "username"
        private val KEY_PASSWORD = "password"
        private val KEY_NAMA = "namapengguna"
        private val KEY_IDROLE = "idrole"
        private val KEY_STATUS = "status"
        private val KEY_FOTO = "foto"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ("
                + "$KEY_IDPENGGUNA INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$KEY_USERNAME TEXT, "
                + "$KEY_PASSWORD TEXT, "
                + "$KEY_NAMA TEXT, "
                + "$KEY_IDROLE INTEGER, "
                + "$KEY_STATUS TEXT, "
                + "$KEY_FOTO TEXT, "
                + "FOREIGN KEY($KEY_IDROLE) REFERENCES Role(idrole))")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPengguna(username : String, password : String ){
        val values = ContentValues()

        values.put(KEY_USERNAME, username)
        values.put(KEY_PASSWORD, password)

        val db = this.writableDatabase

        db.beginTransaction()

        try {
            val result = db.insert(TABLE_NAME, null, values)

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
        val query = "SELECT * FROM $TABLE_NAME WHERE $KEY_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        val usernameExists = cursor.count > 0

        cursor.close()
        db.close()

        return usernameExists
    }

    fun checkCredential(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $KEY_USERNAME = ? AND $KEY_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        val userExists = cursor.count > 0

        cursor.close()
        db.close()

        return userExists
    }

}