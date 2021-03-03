package com.example.rickmortyproject.retrofit

import com.example.rickmortyproject.model.Character
import com.example.rickmortyproject.model.Episode
import com.example.rickmortyproject.model.Results
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
    @GET("api/character/")
    fun getAllCharacter(): Call<Character>

    @GET("api/character/{characterId}")
    fun getCharacterById(@Path("characterId") characterId: Int): Call<Results>

    @GET("api/episode/{episodeNumber}")
    fun getMultipleEpisodeName(@Path("episodeNumber") episodeNumber: String): Call<List<Episode>>

    @GET("api/episode/{episodeNumber}")
    fun getEpisodeName(@Path("episodeNumber") episodeNumber: String): Call<Episode>

    companion object Factory {
        fun createApi(): RetrofitInterface {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}