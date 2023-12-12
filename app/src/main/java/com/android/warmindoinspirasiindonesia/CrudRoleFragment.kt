package com.android.warmindoinspirasiindonesia

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CrudRoleFragment : Fragment(), View.OnClickListener {

    private var idRole = ArrayList<String>()
    private var role = ArrayList<String>()
    private var status = ArrayList<String>()
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var recyclerViewRoles: RecyclerView
    private lateinit var roleListAdapter: RoleListAdapter
    private var ADD_ROLE_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crud_role, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewRoles = view.findViewById(R.id.recyclerViewRoles)
        btnAdd = view.findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this)

        idRole = ArrayList()
        role = ArrayList()
        status = ArrayList()

        storeDataInArrays()

        roleListAdapter = RoleListAdapter(requireContext(), idRole, role, status)
        recyclerViewRoles.adapter = roleListAdapter
        recyclerViewRoles.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                val addRoleIntent = Intent(requireContext(), AddRoleActivity::class.java)
                startActivityForResult(addRoleIntent, ADD_ROLE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ROLE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        idRole.clear()
        role.clear()
        status.clear()

        storeDataInArrays()

        roleListAdapter.notifyDataSetChanged()
    }
    private fun storeDataInArrays() {
        val db = DBHelper(requireContext())
        val cursor: Cursor = db.getAllRoles2()

        if (cursor.count == 0) {
            Toast.makeText(requireContext(), "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idRole.add(cursor.getString(0))
                role.add(cursor.getString(1))
                status.add(cursor.getString(2))
            }
        }
    }
}
