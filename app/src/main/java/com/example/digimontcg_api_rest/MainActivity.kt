package com.example.digimontcg_api_rest

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digimontcg_api_rest.API.APIService
import com.example.digimontcg_api_rest.API.DigimonAdapter
import com.example.digimontcg_api_rest.Class.GenericCard
import com.example.digimontcg_api_rest.databinding.ActivityMainBinding
import com.example.digimontcg_api_rest.popupdialogs.POPUPabout_us_dialog
import com.example.digimontcg_api_rest.popupdialogs.POPUPhelp_dialog
import com.example.digimontcg_api_rest.popupdialogs.POPUPversion_dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var digimonAdapter: DigimonAdapter
    var digimonOutputs = mutableListOf<GenericCard>()
    private lateinit var moreOption: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moreOption = binding.moreOptionsImg

        moreOption.setOnClickListener{
            val menupopup = PopupMenu(this, moreOption)
            menupopup.inflate(R.menu.popup_options_menu)

            menupopup.setOnMenuItemClickListener {item ->
                when(item.itemId) {
                    R.id.menu_item_about_us -> showAboutUs()
                    R.id.menu_item_help -> showHelp()
                    R.id.menu_item_version -> showVersion()
                    R.id.menu_item_close_ses -> onDestroy()
                    R.id.menu_exit -> onDestroy()
                }
                true
            }
            menupopup.show()
        }

        initRecyclerView()
        seach()
    }

    private fun showAboutUs() {
        var pop_about = POPUPabout_us_dialog()
        pop_about.show(supportFragmentManager, "about_us_dialog")
    }

    private fun showHelp() {
        var pop_help = POPUPhelp_dialog()
        pop_help.show(supportFragmentManager, "help_dialog")
    }

    private fun showVersion() {
        var pop_version = POPUPversion_dialog()
        pop_version.show(supportFragmentManager, "version_dialog")
    }

    private fun initRecyclerView() {

        System.out.println("[?] - Inicializando el recicler")
        digimonAdapter = DigimonAdapter(digimonOutputs)
        binding.rvDigimons.layoutManager = LinearLayoutManager(this)
        binding.rvDigimons.adapter = digimonAdapter

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://digimoncard.io/api-public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun seach() {

        var query:String = "getAllCards.php?sort=name&series=Digimon Card Game&sortdirection=asc/"

        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).getDigimonBySerie("$query").execute()
            runOnUiThread {

                System.out.println(call)
                System.out.println(call.errorBody())

                if(call.isSuccessful){

                    try {
                        val digiList = call.body()
                        //System.out.println(call.body())

                        //System.out.println(digiList?.get(0).name/cardnumber)

                        if (digiList != null) {
                            for (item in 0 until digiList.count()){

                                var toAdd = GenericCard (digiList[item].name, digiList[item].cardnumber)

                                //digimonOutputs.clear()
                                digimonOutputs.add(toAdd)

                            }
                        }
                    }catch (e: Exception){
                        System.out.println(e)
                    }

                    //System.out.println("[CHECK]\n")
                    //System.out.println(digimonOutputs)
                    digimonAdapter.notifyDataSetChanged()
                } else {
                    showErrorDialog()
                }
            }
        }

    }

    private fun showErrorDialog() {
        Toast.makeText(this, "Ha habido algun error con la descarga de datos, reinicie la aplicación", Toast.LENGTH_SHORT).show()
    }
}
