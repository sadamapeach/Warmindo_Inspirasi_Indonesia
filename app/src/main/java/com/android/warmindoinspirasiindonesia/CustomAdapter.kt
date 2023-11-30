package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(context: Context, idPengguna: ArrayList<String>, namaPengguna: ArrayList<String>, role: ArrayList<String>, foto: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    private val context: Context = context
    private val idPengguna: ArrayList<String> = idPengguna
    private val namaPengguna: ArrayList<String> = namaPengguna
    private val role: ArrayList<String> = role
    private val foto: ArrayList<String> = foto
    private val dbHelper: DBHelper = DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.pengguna_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvIdPengguna.text = idPengguna[position]
        holder.tvNamaPengguna.text = namaPengguna[position]
        holder.tvRole.text = role[position]

        val imageData: ByteArray? = dbHelper.getImageDataFromDatabase(idPengguna[position]) // Gantilah dengan metode yang sesuai

        if (imageData != null) {
            // Konversi byte array ke Bitmap
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

            // Set Bitmap pada ImageView
            holder.ivFoto.setImageBitmap(bitmap)
        } else {
            // Atur gambar default jika imageData null
            holder.ivFoto.setImageResource(R.drawable.profpic)
        }
    }

    override fun getItemCount(): Int {
        return idPengguna.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIdPengguna: TextView = itemView.findViewById(R.id.tv_idPengguna)
        var tvNamaPengguna: TextView = itemView.findViewById(R.id.tv_namaPengguna)
        var tvRole: TextView = itemView.findViewById(R.id.tv_role)
        var ivFoto: ImageView = itemView.findViewById(R.id.iv_foto)
    }
}
