package com.example.digimontcg_api_rest.API

import com.google.gson.annotations.SerializedName

class FullDigimonRespose (
    @SerializedName("name") var name:String,
    @SerializedName("type") var type:String,
    @SerializedName("color") var color:String,
    @SerializedName("stage") var stage:String,
    @SerializedName("attribute") var attribute:String,
    @SerializedName("level") var level:Int,
    @SerializedName("play_cost") var play_cost:Int,
    @SerializedName("evolution_cost") var evolution_cost:Int,
    @SerializedName("cardrarity") var cardrarity:String,
    @SerializedName("artist") var artist:String,
    @SerializedName("dp") var dp:Int,
    @SerializedName("cardnumber") var cardnumber:String,
    @SerializedName("maineffect") var maineffect:String,
    @SerializedName("soureeffect") var soureeffect:String,
    @SerializedName("set_name") var set_name:String,
    @SerializedName("card_sets") var card_sets:List<String>,
    @SerializedName("image_url") var image_url:String
    )
/*
* dp
*/