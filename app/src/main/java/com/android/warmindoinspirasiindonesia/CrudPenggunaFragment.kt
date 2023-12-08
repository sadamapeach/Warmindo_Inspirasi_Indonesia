package com.android.warmindoinspirasiindonesia

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CrudPenggunaFragment : Fragment(), View.OnClickListener {
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var idPengguna = ArrayList<String>()
    private var namaPengguna = ArrayList<String>()
    private var role = ArrayList<String>()
    private var status = ArrayList<String>()
    private var foto = ArrayList<String>()
    private lateinit var customAdapter: CustomAdapter
    private val ADD_PENGGUNA_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crud_pengguna, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        btnAdd = view.findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this)

        idPengguna = ArrayList()
        namaPengguna = ArrayList()
        role = ArrayList()
        status = ArrayList()
        foto = ArrayList()

        storeDataInArrays()

        customAdapter = CustomAdapter(requireContext(), idPengguna, namaPengguna, role, status, foto)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

//    override fun onClick(view: View?) {
//        if (view != null) {
//            if (view.id == R.id.btn_add) {
//                val addPenggunaIntent = Intent(requireContext(), AddPenggunaActivity::class.java)
//                startActivity(addPenggunaIntent)
//            }
//        }
//    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_add) {
                val addPenggunaIntent = Intent(requireContext(), AddPenggunaActivity::class.java)
                startActivityForResult(addPenggunaIntent, ADD_PENGGUNA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PENGGUNA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateRecyclerView()
        } else if (requestCode == CustomAdapter.EDIT_PENGGUNA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        idPengguna.clear()
        namaPengguna.clear()
        role.clear()
        status.clear()
        foto.clear()

        storeDataInArrays()

        customAdapter.notifyDataSetChanged()
    }

    private fun storeDataInArrays() {
        val db = DBHelper(requireContext())
        val cursor: Cursor = db.getAllPengguna()

        if (cursor.count == 0) {
            Toast.makeText(requireContext(), "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idPengguna.add(cursor.getString(0))
                namaPengguna.add(cursor.getString(3))
                role.add(cursor.getString(7))
                status.add(cursor.getString(4))
            }
        }
    }
}
