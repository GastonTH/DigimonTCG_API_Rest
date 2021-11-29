package com.example.digimontcg_api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.digimontcg_api_rest.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private lateinit var btn_login: Button
    private lateinit var btn_create_acc: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)

        setTheme(R.style.Theme_DigimonTCG_API_Rest)

        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btn_create_acc = binding.btnCreateAcc
        btn_login = binding.btnLogin

        btn_create_acc.setOnClickListener{ goToFormAccount() }
        btn_login.setOnClickListener{ goToMainAPP() }
    }

    private fun goToFormAccount() { startActivity(Intent(this, CreateAcc_Activity::class.java)) }

    private fun goToMainAPP() { startActivity(Intent(this, MainActivity::class.java)) }
}