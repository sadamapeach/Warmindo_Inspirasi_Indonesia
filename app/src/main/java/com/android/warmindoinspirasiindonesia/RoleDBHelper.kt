package com.android.warmindoinspirasiindonesia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class RoleDBHelper(private val context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Warmindo"
        private val TABLE_NAME = "Role"
        private val KEY_IDROLE = "idRole"
        private val KEY_ROLE = "role"
        private val KEY_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + KEY_IDROLE + " INTEGER PRIMARY KEY, " +
                KEY_ROLE + " TEXT," +
                KEY_STATUS + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addRole(idRole : Int, role : String, status : String ){
        val values = ContentValues()

        values.put(KEY_IDROLE, idRole)
        values.put(KEY_ROLE, role)
        values.put(KEY_STATUS, status)

        val db = this.writableDatabase

        try {
            val result = db.insert(TABLE_NAME, null, values)
            db.close()
            if (result != -1L) {
                db.setTransactionSuccessful()
                Toast.makeText(context, "Berhasil menambah role", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Gagal menambah role", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            // Tangani kesalahan jika ada
        }
    }
}