package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class DataTransaksiActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var transaksiPagerAdapter: TransaksiPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_transaksi)

        viewPager = findViewById(R.id.viewpagerTransaki)
        tabLayout = findViewById(R.id.tab_layout)

        transaksiPagerAdapter = TransaksiPagerAdapter(this)
        viewPager.adapter = transaksiPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Shift 1"
                1 -> tab.text = "Shift 2"
            }
        }.attach()
    }
}

//class DataTransaksiActivity : AppCompatActivity(), View.OnClickListener {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var spinnerShift: Spinner
//    private lateinit var datePicker: DatePicker
//    private lateinit var transaksiAdapter: TransaksiAdapter
//    private lateinit var btnShift1: Button
//    private lateinit var btnShift2: Button
//    private lateinit var btnAdd: FloatingActionButton
//
//    private var idTransaksi = ArrayList<String>()
//    private var tanggal = ArrayList<String>()
//    private var total = ArrayList<String>()
//
//    private val db = DBHelper(this)
//
////    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_transaksi)
//
//        recyclerView = findViewById(R.id.recyclerViewTransaksi)
//        btnShift1 = findViewById(R.id.btn_shift1)
//        btnShift2 = findViewById(R.id.btn_shift2)
//        btnAdd = findViewById(R.id.btn_addd)
//        btnShift1.setOnClickListener(this)
//        btnShift2.setOnClickListener(this)
//        btnAdd.setOnClickListener(this)
////        spinnerShift = findViewById(R.id.spinnerShift)
////        datePicker = findViewById(R.id.datePicker)
//
//        transaksiAdapter = TransaksiAdapter(this, idTransaksi, tanggal, total)
//        recyclerView.adapter = transaksiAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
////        populateShiftSpinner()
////        setInitialDate()
//
//
//        idTransaksi = ArrayList()
//        tanggal = ArrayList()
//        total = ArrayList()
//
//        storeDataInArrays()
//
//        transaksiAdapter = TransaksiAdapter(this, idTransaksi, tanggal, total)
//        recyclerView.adapter = transaksiAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val btnOpenDatePicker = findViewById<MaterialButton>(R.id.btnOpenDatePicker)
//
//        btnOpenDatePicker.setOnClickListener {
//            showDatePicker()
//        }
//
//
//
////        spinnerShift.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
////            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
////                updateData()
////            }
////
////            override fun onNothingSelected(parentView: AdapterView<*>) {}
////        }
////
////        datePicker.setOnDateChangedListener { _, _, _, _ ->
////            updateData()
////        }
////
////        updateData()
//    }
//    private fun showDatePicker() {
//        val builder = MaterialDatePicker.Builder.datePicker()
//        val picker = builder.build()
//
//        picker.addOnPositiveButtonClickListener { selection ->
//            // Metode ini dipanggil ketika pengguna memilih tanggal
//            val selectedDateInMillis = selection
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = selectedDateInMillis
//
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val selectedDate = dateFormat.format(calendar.time)
//
//            // Lakukan sesuatu dengan tanggal yang dipilih
//            // Misalnya, tampilkan dalam log atau gunakan untuk operasi lain
//            Log.d("MaterialDatePicker", "Tanggal dipilih: $selectedDate")
//        }
//
//        picker.show(supportFragmentManager, picker.toString())
//    }
////    private fun updateData() {
////        val selectedDate = getDateFromDatePicker(datePicker)
////
////        val selectedShift = spinnerShift.selectedItem.toString()
////
////        val filteredData = filterData(selectedDate, selectedShift)
////
////        transaksiAdapter.updateData(filteredData)
////    }
//
////    private fun filterData(selectedDate: Date, selectedShift: String): List<YourTransactionModel> {
////
////        val cursor: Cursor = db.getFilteredTransactions(selectedDate, selectedShift)
////
////        val filteredData = mutableListOf<YourTransactionModel>()
////
////        if (cursor.count > 0) {
////            while (cursor.moveToNext()) {
////                val id = cursor.getString(0)
////                val date = cursor.getString(1)
////                val totalAmount = cursor.getString(9)
////
////                filteredData.add(YourTransactionModel(id, date, totalAmount))
////            }
////        }
////
////        return filteredData
////    }
////
////    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
////        val day = datePicker.dayOfMonth
////        val month = datePicker.month
////        val year = datePicker.year
////
////        val calendar = Calendar.getInstance()
////        calendar.set(year, month, day)
////
////        return calendar.time
////    }
////
////    private fun populateShiftSpinner() {
////        val shiftOptions = arrayOf("Shift 1", "Shift 2", "Shift 3")
////
////        val adapter = TransaksiAdapter(this, android.R.layout.simple_spinner_item, shiftOptions)
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
////
////        spinnerShift.adapter = adapter
////    }
//
//    override fun onClick(view: View?) {
//        if (view != null) {
//            when (view.id) {
//                R.id.btn_shift1 -> {
//                    idTransaksi.clear()
//                    tanggal.clear()
//                    total.clear()
//
//                    val cursor: Cursor = db.getTransactionsByShift("1")
//
//                    if (cursor.count == 0) {
//                        Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        while (cursor.moveToNext()) {
//                            idTransaksi.add(cursor.getString(0))
//                            tanggal.add(cursor.getString(1))
//                            total.add(cursor.getString(9))
//                        }
//                    }
//
//                    transaksiAdapter.notifyDataSetChanged()
//                }
//                R.id.btn_shift2 -> {
//                    idTransaksi.clear()
//                    tanggal.clear()
//                    total.clear()
//
//                    val cursor: Cursor = db.getTransactionsByShift("2")
//
//                    if (cursor.count == 0) {
//                        Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        while (cursor.moveToNext()) {
//                            idTransaksi.add(cursor.getString(0))
//                            tanggal.add(cursor.getString(1))
//                            total.add(cursor.getString(9))
//                        }
//                    }
//
//                    transaksiAdapter.notifyDataSetChanged()
//                }
//            }
//        }
//    }
//
//
//    private fun storeDataInArrays() {
//        val db = DBHelper(this)
//        val cursor: Cursor = db.getAllTransaksi()
//
//        if (cursor.count == 0) {
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
//        } else {
//            while (cursor.moveToNext()) {
//                idTransaksi.add(cursor.getString(0))
//                tanggal.add(cursor.getString(1))
//                total.add(cursor.getString(9))
//            }
//        }
//    }
//}
