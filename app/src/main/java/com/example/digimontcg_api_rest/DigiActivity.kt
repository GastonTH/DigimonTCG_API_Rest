package com.example.digimontcg_api_rest

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.example.digimontcg_api_rest.API.*
import com.example.digimontcg_api_rest.Class.Digimon
import com.example.digimontcg_api_rest.databinding.ActivityDigiBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

// SIENTO USAR BINDING Y FINDVIEWBYID, binding no le apetece funcionar correctamente en este proyecto

class DigiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDigiBinding
    private lateinit var outName: String
    private lateinit var outCardNumber: String
    private lateinit var thisDigiInfo: Digimon
    private lateinit var img_card: ImageView
    private lateinit var go_back: ImageView
    private lateinit var more_options_digi: ImageButton

    // private lateinit var floatColor: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Inicializamos el binding
        binding = ActivityDigiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //recuperamos los campos que paso por el putExtra()
        outName = intent.getStringExtra(SEND_CARD_NAME).toString()
        outCardNumber = intent.getStringExtra(SEND_CARD_CARDNUMBER).toString()

        // Añadimos funcionalidad a las variables inicializadas anteriormente
        // floatColor = findViewById(R.id.float_color)
        img_card = binding.imgCard
        go_back = findViewById(R.id.go_back)
        more_options_digi = findViewById(R.id.more_options_digi)

        //Funcionalidad de los botones
        go_back.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        more_options_digi.setOnClickListener{
            val menupopup = PopupMenu(this, more_options_digi)
            menupopup.inflate(R.menu.menu_digi)

            menupopup.setOnMenuItemClickListener {item ->
                when(item.itemId) {
                    R.id.menu_item_moreinfo -> ""
                }
                true
            }
            menupopup.show()
        }

        // inicializamos el digimon a "vacio"
        var url_img = "https://cf.geekdo-images.com/-IkPf3T_WlHGfBHXQQxTYg__opengraph/img/kQNZuA7ZBZLHrBkTrfOqQGjBVwk=/fit-in/1200x630/filters:strip_icc()/pic5382032.jpg"
        thisDigiInfo = Digimon(
            "","","","","",
            0,0,0,"",
            "",0,"","","","",listOf(""),url_img
        )

        // Le damos un aviso al usuario
        Toast.makeText(this, "Es posible que la imagen tarde en cargar, dependiendo de su conexion de internet", Toast.LENGTH_SHORT).show()
        actualizar()
        seach()
    }

    // actualizacion de la informacion del view
    private fun actualizar() {

        System.out.println("[?] - Cargando datos de la vista...")

        var randomNumber:Int = Random.nextInt(0, 3)

        when (randomNumber){

            0 -> Picasso.get().load(R.drawable.back_digi_activity_1).into(binding.imgHeader)
            1 -> Picasso.get().load(R.drawable.back_digi_activity_2).into(binding.imgHeader)
            2 -> Picasso.get().load(R.drawable.back_digi_activity_3).into(binding.imgHeader)
            3 -> Picasso.get().load(R.drawable.back_digi_activity_4).into(binding.imgHeader)
        }

        // Aqui asigno el color del floating button
        when(thisDigiInfo.color){
            "Red" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(231, 76, 60)))
            "Yellow" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(252, 243, 207)))
            "Blue" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(52, 152, 219)))
            "Black" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(23, 32, 42)))
            "Green" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(26, 188, 156)))
            "Purple" -> findViewById<FloatingActionButton>(R.id.floating_butt_color).backgroundTintList = (ColorStateList.valueOf(Color.rgb(142, 68, 173)))
        }

        Picasso.get().load(thisDigiInfo.image_url).into(img_card)

        binding.txtDiginame.text = thisDigiInfo.name
        findViewById<TextView>(R.id.txt_cardnumber).text = thisDigiInfo.cardnumber

        findViewById<TextView>(R.id.txtInmaineffect).text = thisDigiInfo.maineffect
        findViewById<TextView>(R.id.txt_insourceeffect).text = thisDigiInfo.soureeffect
        findViewById<TextView>(R.id.txt_type_digi).text = thisDigiInfo.type
        findViewById<TextView>(R.id.txt_artist).text = thisDigiInfo.artist
        findViewById<TextView>(R.id.txt_atributte).text = thisDigiInfo.attribute
        findViewById<TextView>(R.id.txt_set).text = thisDigiInfo.set_name
        findViewById<TextView>(R.id.txt_rarity).text = thisDigiInfo.cardrarity
        findViewById<TextView>(R.id.txt_inDp).text = thisDigiInfo.dp.toString()
        findViewById<TextView>(R.id.txt_inEvo_cost).text = thisDigiInfo.evolution_cost.toString()
        findViewById<TextView>(R.id.txt_inPlay_cost).text = thisDigiInfo.play_cost.toString()
        findViewById<TextView>(R.id.txt_inLvl).text = thisDigiInfo.level.toString()

        // intent para abrir la foto en un navegador
        img_card.setOnClickListener{
            val url = thisDigiInfo.image_url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

    // funcion para buscar en la api
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://digimoncard.io/api-public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // llamada a la api asincronamente con los campos recogidos del intent anterior
    private fun seach() {

        var query: String = "https://digimoncard.io/api-public/search.php?n=$outName&card=$outCardNumber&series=Digimon%20Card%20Game"

        System.out.println(query)

        CoroutineScope(Dispatchers.IO).launch {

            val call =
                getRetrofit().create(APIService::class.java).getDigimonByNameAndCard("$query").execute()
            runOnUiThread {

                val digiListInfo = call.body()

                if (call.isSuccessful) {

                    var digiErrors = DigiError()

                    if (digiListInfo != null){

                        if (digiListInfo[0].type == null){
                            digiListInfo[0].type == "No tiene tipo"
                        }

                        if (digiListInfo[0].attribute == null){
                            digiListInfo[0].attribute = digiErrors.errorAttribute()
                        }

                        if (digiListInfo[0].soureeffect == null){
                            digiListInfo[0].soureeffect = digiErrors.errorSoureeffect()
                        }

                        if (digiListInfo[0].stage == null){
                            digiListInfo[0].stage = digiErrors.errorStage()
                        }

                        if(digiListInfo[0].maineffect == null){
                            digiListInfo[0].maineffect = digiErrors.errorMaineffect()
                        }
                        if(digiListInfo[0].artist == null){
                            digiListInfo[0].artist = "Anónimo"
                        }

                        thisDigiInfo = Digimon(
                                digiListInfo[0].name,
                                digiListInfo[0].type,
                                digiListInfo[0].color,
                                digiListInfo[0].stage,
                                digiListInfo[0].attribute,
                                digiListInfo[0].level,
                                digiListInfo[0].play_cost,
                                digiListInfo[0].evolution_cost,
                                digiListInfo[0].cardrarity,
                                digiListInfo[0].artist,
                                digiListInfo[0].dp,
                                digiListInfo[0].cardnumber,
                                digiListInfo[0].maineffect,
                                digiListInfo[0].soureeffect,
                                digiListInfo[0].set_name,
                                digiListInfo[0].card_sets,
                                digiListInfo[0].image_url
                        )
                        System.out.println(thisDigiInfo)
                        actualizar()
                    }

                } else {
                    showErrorDialog()
                }
            }
        }
    }

    // cuando miestra el error
    private fun showErrorDialog() {
        Toast.makeText(this, "Ha ocurrido un error al mostrar tu digimon :), recarga la aplicación", Toast.LENGTH_SHORT).show()
    }
}