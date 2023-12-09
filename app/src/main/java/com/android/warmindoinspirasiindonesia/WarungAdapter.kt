package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WarungAdapter(context: Context, idWarung: ArrayList<String>, namaWarung: ArrayList<String>, logo: ArrayList<String>) :
    RecyclerView.Adapter<WarungAdapter.MyViewHolder>() {

    private val context: Context = context
    private val idWarung: ArrayList<String> = idWarung
    private val namaWarung: ArrayList<String> = namaWarung
    private val logo: ArrayList<String> = logo
    private val dbHelper: DBHelper = DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_warung, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvIdWarung.text = idWarung[position]
        holder.tvNamaWarung.text = namaWarung[position]


        val imageData: ByteArray? = dbHelper.getAllLogoWarung(idWarung[position])

        if (imageData != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            holder.ivLogo.setImageBitmap(bitmap)
        } else {
            holder.ivLogo.setImageResource(R.drawable.pic_add)
        }
    }

    override fun getItemCount(): Int {
        return idWarung.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIdWarung: TextView = itemView.findViewById(R.id.tv_idWarung)
        var tvNamaWarung: TextView = itemView.findViewById(R.id.tv_namaWarung)
        var ivLogo: ImageView = itemView.findViewById(R.id.iv_logo)
    }
}
