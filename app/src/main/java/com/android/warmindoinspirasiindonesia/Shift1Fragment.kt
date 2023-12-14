package com.android.warmindoinspirasiindonesia

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.google.android.material.button.MaterialButton
class Shift1Fragment : Fragment(), View.OnClickListener{

    private var idTransaksi = ArrayList<String>()
    private var tanggal = ArrayList<String>()
    private var total = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var transaksiListAdapter: TransaksiAdapter
    private lateinit var btnOpenDatePicker: MaterialButton
    private var selectedDate: String? = null
    private val selectedShift: String = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shift1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewTransaksi1)
        btnOpenDatePicker = view.findViewById(R.id.btnOpenDatePicker)

        idTransaksi = ArrayList()
        tanggal = ArrayList()
        total = ArrayList()

        storeDataInArrays()
        btnOpenDatePicker.setOnClickListener(this)

        transaksiListAdapter = TransaksiAdapter(requireContext(), idTransaksi, tanggal, total)
        recyclerView.adapter = transaksiListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btnOpenDatePicker) {
                showDatePicker()
            }
        }
    }
    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            val selectedDateInMillis = selection
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDateInMillis

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = dateFormat.format(calendar.time)

            updateData()
        }

        picker.show(childFragmentManager, picker.toString())
    }


    private fun updateData() {
        val db = DBHelper(requireContext())
        val cursor: Cursor = db.getFilteredTransactions(selectedDate, selectedShift)

        idTransaksi.clear()
        tanggal.clear()
        total.clear()

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                idTransaksi.add(cursor.getString(0))
                tanggal.add(cursor.getString(1))
                total.add(cursor.getString(9))
            }
        }

        transaksiListAdapter.notifyDataSetChanged()
    }

    private fun storeDataInArrays() {
        val db = DBHelper(requireContext())
        val cursor: Cursor = db.getTransactionsByShift(selectedShift)

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
