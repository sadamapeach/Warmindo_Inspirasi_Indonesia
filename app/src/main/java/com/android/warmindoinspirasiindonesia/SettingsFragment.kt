package com.android.warmindoinspirasiindonesia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(), View.OnClickListener {
    private lateinit var btnLogout: Button
    private lateinit var btnTransaksi: Button
    private lateinit var btnWarung: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnTransaksi = view.findViewById(R.id.btn_transaksi)
        btnLogout = view.findViewById(R.id.btn_logout)
        btnWarung = view.findViewById(R.id.btn_warung)

        btnTransaksi.setOnClickListener(this)
        btnLogout.setOnClickListener(this)
        btnWarung.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_logout -> {
                    val logoutIntent = Intent(requireContext(), LogoutActivity::class.java)
                    startActivity(logoutIntent)
                }
                R.id.btn_transaksi -> {
                    val addTransaksiActivity = Intent(requireContext(), AddTransaksiActivity::class.java)
                    startActivity(addTransaksiActivity)
                }
                R.id.btn_warung -> {
                    val addWarungActivity = Intent(requireContext(), AddWarungActivity::class.java)
                    startActivity(addWarungActivity)
                }
            }
        }
    }
}
