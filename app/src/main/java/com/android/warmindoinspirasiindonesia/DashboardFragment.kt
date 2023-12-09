package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment(), View.OnClickListener {
    private lateinit var btnViewTransaksi: Button
    private lateinit var btnViewWarung: Button
    private lateinit var tvIncomeBulan: TextView
    private lateinit var tvIncomeHari: TextView
    private lateinit var tvCountTransaksi: TextView
    private lateinit var tvCountWarung: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        tvIncomeBulan = view.findViewById(R.id.tv_incomeBulan)
        tvIncomeHari = view.findViewById(R.id.tv_incomeHari)
        tvCountTransaksi = view.findViewById(R.id.tv_countTransaksi)
        tvCountWarung = view.findViewById(R.id.tv_countWarung)
        btnViewTransaksi = view.findViewById(R.id.btn_viewTransaksi)
        btnViewWarung = view.findViewById(R.id.btn_viewWarung)

        btnViewTransaksi.setOnClickListener(this)
        btnViewWarung.setOnClickListener(this)

        showTotalHariIni()
        showTotalBulanIni()
        showJumlahTransaksi()
        showJumlahWarung()

        return view
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_viewTransaksi -> {
                    val dataTransaksiIntent = Intent(requireContext(), DataTransaksiActivity::class.java)
                    startActivity(dataTransaksiIntent)
                }
                R.id.btn_viewWarung -> {
                    val dataWarungIntent = Intent(requireContext(), DataWarungActivity::class.java)
                    startActivity(dataWarungIntent)
                }
            }
        }
    }
    private fun showTotalHariIni() {
        val dbHelper = DBHelper(requireContext())
        val totalHariIni = dbHelper.getTotalHariIni()

        tvIncomeHari.text = "$totalHariIni"
    }

    private fun showTotalBulanIni() {
        val dbHelper = DBHelper(requireContext())
        val totalBulanIni = dbHelper.getTotalBulanIni()

        tvIncomeBulan.text = "$totalBulanIni"
    }

    private fun showJumlahTransaksi() {
        val dbHelper = DBHelper(requireContext())
        val jumlahTransaksi = dbHelper.getJumlahTransaksi()

        tvCountTransaksi.text = "$jumlahTransaksi"
    }

    private fun showJumlahWarung() {
        val dbHelper = DBHelper(requireContext())
        val jumlahWarung = dbHelper.getJumlahWarung()

        tvCountWarung.text = "$jumlahWarung"
    }

}
