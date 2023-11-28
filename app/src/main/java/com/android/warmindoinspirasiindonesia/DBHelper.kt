package com.android.warmindoinspirasiindonesia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 3
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
                KEY_PENGGUNA_FOTO + " TEXT, " +
                KEY_PENGGUNA_IDROLE + " INTEGER, " +
                "FOREIGN KEY(" + KEY_PENGGUNA_IDROLE + ") REFERENCES " + TABLE_ROLE + "(" + KEY_ROLE_IDROLE + ")," +
                KEY_ROLE_STATUS + " TEXT" + ")")

        db.execSQL(queryPengguna)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGGUNA)
        onCreate(db)
    }

    fun addUser(username : String, password : String ){
        val values = ContentValues()

        values.put(KEY_USERS_USERNAME, username)
        values.put(KEY_USERS_PASSWORD, password)

        val db = this.writableDatabase

        db.beginTransaction()

        try {
            val result = db.insert(TABLE_USERS, null, values)

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
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_USERS_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        val usernameExists = cursor.count > 0

        cursor.close()
        db.close()

        return usernameExists
    }

    fun checkCredential(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_USERS_USERNAME = ? AND $KEY_USERS_PASSWORD = ?"
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

    fun addPengguna(username : String, password : String, nama : String, idRole : Int, status : String, foto : String){
        val values = ContentValues()

        values.put(KEY_PENGGUNA_USERNAME, username)
        values.put(KEY_PENGGUNA_PASSWORD, password)
        values.put(KEY_PENGGUNA_NAMA, nama)
        values.put(KEY_PENGGUNA_IDROLE, idRole)
        values.put(KEY_PENGGUNA_FOTO, foto)
        values.put(KEY_PENGGUNA_STATUS, status)

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

}