package com.android.warmindoinspirasiindonesia

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(context: Context, idPengguna: ArrayList<String>,
                    namaPengguna: ArrayList<String>, role: ArrayList<String>,
                    status: ArrayList<String>, foto: ArrayList<String>,  private val onActivityResultCallback: (requestCode: Int, resultCode: Int) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>(), DataUpdateListener {
    companion object {
        const val EDIT_PENGGUNA_REQUEST_CODE = 1
    }

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
        //holder.tvIdPengguna.text = idPengguna[position]
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
            (context as AppCompatActivity).startActivityForResult(intent, EDIT_PENGGUNA_REQUEST_CODE)
        }

        holder.btnDelete.setOnClickListener {
            confirmDialog(holder, position)
        }

        val imageData: ByteArray? = dbHelper.getAllFotoPengguna(idPengguna[position])

        if (imageData != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            holder.ivFoto.setImageBitmap(bitmap)
        } else {
            holder.ivFoto.setImageResource(R.drawable.profpic)
        }
    }

    fun processActivityResult(requestCode: Int, resultCode: Int) {
        onActivityResultCallback.invoke(requestCode, resultCode)
    }

    override fun onDataUpdated() {
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        idPengguna.clear()
        namaPengguna.clear()
        role.clear()
        status.clear()
        foto.clear()

        storeDataInArrays()

        notifyDataSetChanged()
    }

    private fun storeDataInArrays() {
        val db = DBHelper(context)
        val cursor: Cursor = db.getAllPengguna()

        if (cursor.count == 0) {
            Toast.makeText(context, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idPengguna.add(cursor.getString(0))
                namaPengguna.add(cursor.getString(3))
                role.add(cursor.getString(7))
                status.add(cursor.getString(4))
            }
        }
    }

    fun confirmDialog(holder: MyViewHolder, position: Int) {
        val builder = AlertDialog.Builder(holder.itemView.context)
        builder.setTitle("Konfirmasi Penghapusan")
        builder.setMessage("Apakah Anda yakin ingin menghapus ${holder.tvNamaPengguna.text}?")

        builder.setPositiveButton("Yes") { _, _ ->
            val db = DBHelper(holder.itemView.context)
            db.deletePengguna(idPengguna[position])

            idPengguna.removeAt(position)
            namaPengguna.removeAt(position)
            status.removeAt(position)
            role.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        builder.setNegativeButton("No") { _, _ ->
            // Do nothing if "No" is clicked
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun getItemCount(): Int {
        return idPengguna.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var tvIdPengguna: TextView = itemView.findViewById(R.id.tv_idPengguna)
        var tvNamaPengguna: TextView = itemView.findViewById(R.id.tv_namaPengguna)
        var tvRole: TextView = itemView.findViewById(R.id.tv_role)
        var tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        var ivFoto: ImageView = itemView.findViewById(R.id.iv_foto)
        var btnEdit: ImageView = itemView.findViewById(R.id.btn_edit)
        var btnDelete: ImageView = itemView.findViewById(R.id.btn_delete)
    }
}
