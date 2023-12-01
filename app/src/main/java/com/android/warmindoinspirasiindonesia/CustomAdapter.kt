package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(context: Context, idPengguna: ArrayList<String>, namaPengguna: ArrayList<String>, role: ArrayList<String>, status: ArrayList<String>, foto: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    private val context: Context = context
    private val idPengguna: ArrayList<String> = idPengguna
    private val namaPengguna: ArrayList<String> = namaPengguna
    private val role: ArrayList<String> = role
    private val status: ArrayList<String> = status
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
        holder.tvStatus.text = status[position]

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, UpdatePenggunaActivity::class.java)
            val foto: ByteArray? = dbHelper.getFotoPengguna(idPengguna[position])
            intent.putExtra("id", idPengguna[position])
            intent.putExtra("nama", namaPengguna[position])
            intent.putExtra("status", status[position])
            intent.putExtra("role", role[position])
            intent.putExtra("foto", foto)
            context.startActivity(intent)
        }

        val imageData: ByteArray? = dbHelper.getAllFotoPengguna(idPengguna[position])

        if (imageData != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            holder.ivFoto.setImageBitmap(bitmap)
        } else {
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
        var tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        var ivFoto: ImageView = itemView.findViewById(R.id.iv_foto)
        var btnEdit: Button = itemView.findViewById(R.id.btn_edit)
    }
}
