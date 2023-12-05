package com.android.warmindoinspirasiindonesia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegis: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirmPassword)
        btnRegis = findViewById(R.id.btn_regis)

        btnRegis.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.btn_regis) {
                val db = DBHelper(this)

                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (password == confirmPassword) {
                        val checkUsername = db.checkUsername(username)
                        if (!checkUsername) {
                            val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())
                            db.addUser(username, hashedPassword)

                            etUsername.text.clear()
                            etPassword.text.clear()
                            etConfirmPassword.text.clear()
                        } else {
                            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}