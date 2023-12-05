package com.android.warmindoinspirasiindonesia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransaksiAdapter(context: Context, idTransaksi: ArrayList<String>, tanggal: ArrayList<String>, total: ArrayList<String>) :
    RecyclerView.Adapter<TransaksiAdapter.MyViewHolder>() {

    private val context: Context = context
    private val idTransaksi: ArrayList<String> = idTransaksi
    private val tanggal: ArrayList<String> = tanggal
    private val total: ArrayList<String> = total
    private val dbHelper: DBHelper = DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_transaksi, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvIdTransaksi.text = idTransaksi[position]
        holder.tvTanggal.text = tanggal[position]
        holder.tvTotal.text = total[position]
    }

    override fun getItemCount(): Int {
        return idTransaksi.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIdTransaksi: TextView = itemView.findViewById(R.id.tv_idTransaksi)
        var tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
        var tvTotal: TextView = itemView.findViewById(R.id.tv_total)
    }
}
