package com.android.warmindoinspirasiindonesia
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoleListAdapter(
    private val context: Context,
    idRole: ArrayList<String>,
    role: ArrayList<String>,
    status: ArrayList<String>
) :
    RecyclerView.Adapter<RoleListAdapter.ViewHolder>() {

    private var roleList: List<Roles> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdRole: TextView = itemView.findViewById(R.id.tvIdRole)
        val tvRole: TextView = itemView.findViewById(R.id.tvRole)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    // Fungsi untuk memperbarui data dalam adapter
    fun updateData(newRoleList: List<Roles>) {
        roleList = newRoleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_role, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val role = roleList[position]

        holder.tvIdRole.text = role.idRole.toString()
        holder.tvRole.text = role.role
        holder.tvStatus.text = role.status

        holder.btnEdit.setOnClickListener {
            // Aksi saat tombol Edit diklik
            // Contoh: Intent untuk membuka halaman edit
            val editIntent = Intent(context, EditRoleActivity::class.java)
            editIntent.putExtra("idRole", role.idRole)
            context.startActivity(editIntent)
        }

        holder.btnDelete.setOnClickListener {
            // Aksi saat tombol Delete diklik
            // Contoh: Hapus data dari database dan perbarui adapter
            val dbHelper = DBHelper(context)
            dbHelper.deleteRole(role.idRole)
            updateData(dbHelper.getAllRoles())
        }
    }

    override fun getItemCount(): Int {
        return roleList.size
    }
}