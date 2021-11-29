package com.example.digimontcg_api_rest.API

import com.google.gson.annotations.SerializedName

// esta clase recoge los valores del json al view
class DigimonResponse (
    @SerializedName("name") var name:String,
    @SerializedName("cardnumber") var cardnumber:String
    )