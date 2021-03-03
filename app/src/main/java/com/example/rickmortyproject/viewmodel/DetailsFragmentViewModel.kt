package com.example.rickmortyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickmortyproject.model.Episode
import com.example.rickmortyproject.model.Results
import com.example.rickmortyproject.retrofit.RetrofitInterface
import com.example.rickmortyproject.utils.CHARACTER_ID
import retrofit2.Call
import retrofit2.Response

class DetailsFragmentViewModel : ViewModel() {
    //LiveData для персонажа, списка эпизодов и одного эпизода. Запрос возвращает не возвращает лист если объект один.
    var liveDataCharacter = MutableLiveData<Results>()
    var liveDataMultipleEpisode = MutableLiveData<List<Episode>>()
    var liveDataSingleEpisode = MutableLiveData<Episode>()

    init {
        var call: Call<Results> = RetrofitInterface.createApi().getCharacterById(CHARACTER_ID)
        call.enqueue(object : retrofit2.Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    response.body()?.let { liveDataCharacter.value = it }
                    response.body()?.let { getEpisodeName(it.episode) }
                } else {
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun getEpisodeName(listEpisode: List<String>) {
        var allEpisodeString = ""
        //Респонс списка эпизодов представляет из себя кучу гиперссылок. Соответсвенно в итоге нужно получить из них массив цифр для дальнейшего запроса имён эпизодов
        //Переводим всё в одну строку
        for (episode in listEpisode) {
            allEpisodeString += " $episode"
        }
        //Убираем все лищние символы и заменяем пробелы на запятые
        val regex = Regex("[.:/A-Za-z]")
        var episodeListNumber = regex.replace(allEpisodeString, "").trim().replace(" ", ",")
        //Если символов в строке больше двух, значит количество эпизодов больше одного, будет возвращен список.
        if (episodeListNumber.length > 2) {
            var call: Call<List<Episode>> =
                RetrofitInterface.createApi().getMultipleEpisodeName(episodeListNumber)
            call.enqueue(object : retrofit2.Callback<List<Episode>> {
                override fun onResponse(
                    call: Call<List<Episode>>,
                    response: Response<List<Episode>>
                ) {
                    if (response.isSuccessful) {
                        liveDataMultipleEpisode.value = response.body()
                    } else {
                        println(response.errorBody())
                    }
                }

                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                    println(t.message)
                }
            })
            //Иначе возвращается один объект
        } else {
            var call: Call<Episode> =
                RetrofitInterface.createApi().getEpisodeName(episodeListNumber)
            call.enqueue(object : retrofit2.Callback<Episode> {
                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                    if (response.isSuccessful) {
                        liveDataSingleEpisode.value = response.body()
                    } else {
                        println(response.errorBody())
                    }
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    println(t.message)
                }
            })
        }
    }
}
