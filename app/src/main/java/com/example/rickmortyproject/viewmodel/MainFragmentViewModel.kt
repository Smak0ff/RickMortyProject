package com.example.rickmortyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickmortyproject.model.Character
import com.example.rickmortyproject.model.Results
import com.example.rickmortyproject.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Response

class MainFragmentViewModel : ViewModel() {
    //LiveData для списка персонаэей
    var liveData = MutableLiveData<List<Results>>()

    init {
        var call: Call<Character> = RetrofitInterface.createApi().getAllCharacter()
        call.enqueue(object : retrofit2.Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful) {
                    response.body()?.let { liveData.value = it.results }
                } else {
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                println(t.message)
            }
        })
    }
}