package com.android.warmindoinspirasiindonesia

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoleListAdapter(context: Context, idRole: ArrayList<String>, role: ArrayList<String>, status: ArrayList<String>) :
    RecyclerView.Adapter<RoleListAdapter.MyViewHolder>() {

    private val context: Context = context
    private val idRole: ArrayList<String> = idRole
    private val role: ArrayList<String> = role
    private val status: ArrayList<String> = status

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_role, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvIdRole.text = idRole[position]
        holder.tvRole.text = role[position]
        holder.tvStatus.text = status[position]

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, EditRoleActivity::class.java)
            intent.putExtra("idRole", idRole[position])
            intent.putExtra("role", role[position])
            intent.putExtra("status", status[position])
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            confirmDialog(holder, position)
        }
    }

    private fun confirmDialog(holder: MyViewHolder, position: Int) {
        val builder = AlertDialog.Builder(holder.itemView.context)
        builder.setTitle("Konfirmasi Penghapusan")
        builder.setMessage("Apakah Anda yakin ingin menghapus ${holder.tvRole.text}?")

        builder.setPositiveButton("Yes") { _, _ ->
            val db = DBHelper(holder.itemView.context)
            db.deleteRole(idRole[position])

            idRole.removeAt(position)
            role.removeAt(position)
            status.removeAt(position)
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
        return idRole.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIdRole: TextView = itemView.findViewById(R.id.tv_idRole)
        var tvRole: TextView = itemView.findViewById(R.id.tvRole)
        var tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        var btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        var btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }
}
