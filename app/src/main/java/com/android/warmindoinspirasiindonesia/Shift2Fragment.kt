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

class Shift2Fragment : Fragment(){

    private var idTransaksi = ArrayList<String>()
    private var tanggal = ArrayList<String>()
    private var total = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var transaksiListAdapter: TransaksiAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shift2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewTransaksi2)


        idTransaksi = ArrayList()
        tanggal = ArrayList()
        total = ArrayList()

        storeDataInArrays()

        transaksiListAdapter = TransaksiAdapter(requireContext(), idTransaksi, tanggal, total)
        recyclerView.adapter = transaksiListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun storeDataInArrays() {
        val db = DBHelper(requireContext())
        val cursor: Cursor = db.getTransactionsByShift("2")

        if (cursor.count == 0) {
            Toast.makeText(requireContext(), "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                idTransaksi.add(cursor.getString(0))
                tanggal.add(cursor.getString(1))
                total.add(cursor.getString(9))
            }
        }
    }
}
