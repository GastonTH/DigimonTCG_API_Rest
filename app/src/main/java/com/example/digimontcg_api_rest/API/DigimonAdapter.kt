package com.example.digimontcg_api_rest.API

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digimontcg_api_rest.Class.GenericCard
import com.example.digimontcg_api_rest.DigiActivity
import com.example.digimontcg_api_rest.R
import com.example.digimontcg_api_rest.databinding.ItemGenericCardBinding
import com.squareup.picasso.Picasso
import kotlin.random.Random

const val SEND_CARD_NAME = "com.example.digimontcg_api.API.{CARD_NAME}"
const val SEND_CARD_CARDNUMBER = "com.example.digimontcg_api.API.{CARD_CARDNUMBER}"

class DigimonAdapter(val digiList: List<GenericCard>): RecyclerView.Adapter<DigimonAdapter.ViewHolder>() {

    // este metodo creaun nuevo view, y este lo asociamos al contexto
    // y finalmente lo devolvemos
    // exactamente estamos creando un view y le estamon inflando el item_digimon

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_generic_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = digiList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return digiList.size
    }

    class ViewHolder(viewContext: View) : RecyclerView.ViewHolder(viewContext) {

        private val binding = ItemGenericCardBinding.bind(viewContext)

        private val nameDigi: TextView = binding.txtDigimonName
        private val cardDigi: TextView = binding.txtDigimonCardNumber
        private val card_view: LinearLayout = binding.fullCard
        private val img_recycler: ImageView = binding.imgRecycler


        fun bind(card: GenericCard){

            var randomNumber:Int = Random.nextInt(0, 6)

            System.out.println("random " + randomNumber.toString())

            when (randomNumber){
                0 -> Picasso.get().load(R.drawable.digiegg_6).into(img_recycler)
                1 -> Picasso.get().load(R.drawable.digiegg_1).into(img_recycler)
                2 -> Picasso.get().load(R.drawable.digiegg_2).into(img_recycler)
                3 -> Picasso.get().load(R.drawable.digiegg_3).into(img_recycler)
                4 -> Picasso.get().load(R.drawable.digiegg_4).into(img_recycler)
                5 -> Picasso.get().load(R.drawable.digiegg_5).into(img_recycler)
            }

            nameDigi.text = card.name
            cardDigi.text = card.cardnumber
            card_view.setOnClickListener{ goToFullCard(card) }
        }

        private fun goToFullCard(card: GenericCard) {

            val intent= Intent(binding.root.context, DigiActivity::class.java)
            intent.putExtra(SEND_CARD_NAME, card.name)
            intent.putExtra(SEND_CARD_CARDNUMBER, card.cardnumber)

            binding.root.context.startActivity(intent)
        }
    }
}