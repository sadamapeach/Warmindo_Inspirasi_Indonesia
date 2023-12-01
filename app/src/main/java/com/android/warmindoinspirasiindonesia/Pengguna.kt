package com.android.warmindoinspirasiindonesia

data class Pengguna(
    val idPengguna: String,
    val username: String,
    val password: String,
    val nama: String,
    val role: Int,
    val status: String,
    val foto: ByteArray?
)

