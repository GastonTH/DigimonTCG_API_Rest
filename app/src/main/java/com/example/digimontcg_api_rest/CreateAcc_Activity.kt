package com.example.digimontcg_api_rest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.digimontcg_api_rest.databinding.ActivityCreatrAccBinding

class CreateAcc_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatrAccBinding
    private lateinit var btn_sign_in: Button
    private var listaEmail: Array<String> = arrayOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatrAccBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btn_sign_in = binding.btnSignIn

        btn_sign_in.setOnClickListener{
            val email = "blacknoctishiller@gmail.com"
            val intentEmail = Intent(Intent.ACTION_SEND, Uri.parse(email))
            intentEmail.type = "plain/text"
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Titulo del mail")
            intentEmail.putExtra(Intent.EXTRA_TEXT, "Hola, estoy esperando respuesta..")
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(""))
            startActivity(Intent.createChooser(intentEmail, "Elige cliente de correo"))
            Toast.makeText(this, "Enviando correo...", Toast.LENGTH_SHORT).show()
            //startActivity( Intent(this, MainActivity::class.java))
        }
    }
}