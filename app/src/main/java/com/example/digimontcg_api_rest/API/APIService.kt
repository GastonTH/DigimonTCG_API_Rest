package com.example.digimontcg_api_rest.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getDigimonBySerie(@Url url:String): Call<List<DigimonResponse>>

    @GET
    fun getDigimonByNameAndCard(@Url url:String): Call<List<FullDigimonRespose>>
}