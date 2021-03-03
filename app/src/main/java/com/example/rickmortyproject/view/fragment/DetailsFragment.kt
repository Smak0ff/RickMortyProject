package com.example.rickmortyproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickmortyproject.R
import com.example.rickmortyproject.databinding.FragmentDetailsBinding
import com.example.rickmortyproject.viewmodel.DetailsFragmentViewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var mBinding: FragmentDetailsBinding
    private lateinit var mTextViewName: TextView
    private lateinit var mTextViewStatus: TextView
    private lateinit var mTextViewSpecies: TextView
    private lateinit var mTextViewType: TextView
    private lateinit var mTextViewGender: TextView
    private lateinit var mTextViewEpisode: TextView
    private lateinit var mTextViewCreated: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mViewModel: DetailsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Инициализация ViewBinding
        mBinding = FragmentDetailsBinding.inflate(layoutInflater)
        mTextViewName = mBinding.characterNameId
        mTextViewStatus = mBinding.characterStatusId
        mTextViewSpecies = mBinding.characterSpeciesId
        mTextViewType = mBinding.characterTypeId
        mTextViewGender = mBinding.characterGenderId
        mTextViewEpisode = mBinding.characterEpisodeId
        mTextViewCreated = mBinding.characterCreatedId
        mImageView = mBinding.characterImageId
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(this).get(DetailsFragmentViewModel::class.java)
        //Слушатель для списка имен эпизодов
        mViewModel.liveDataMultipleEpisode.observe(this, Observer {
            var listEpisode = ""
            for (episode in it) {
                listEpisode += ("${episode.name}, ")
            }
            mTextViewEpisode.text = ("${getString(R.string.character_edisode)}  $listEpisode")
        })
        //Слушатель для имени одного эпизода
        mViewModel.liveDataSingleEpisode.observe(this, Observer {
            mTextViewEpisode.text = ("${getString(R.string.character_edisode)}  ${it.name}")
        })
        //Слушатель для получения объекта персонажа
        mViewModel.liveDataCharacter.observe(this, Observer {
            Glide.with(this)
                .load(it?.image)
                .into(mImageView)
            mTextViewName.text = ("${getString(R.string.character_name)} ${checkData(it.name)}")
            mTextViewStatus.text =
                ("${getString(R.string.character_status)} ${checkData(it.status)}")
            mTextViewSpecies.text =
                ("${getString(R.string.character_species)}  ${checkData(it.species)}")
            mTextViewType.text = ("${getString(R.string.character_type)}  ${checkData(it.type)}")
            mTextViewGender.text =
                ("${getString(R.string.character_gender)}  ${checkData(it.gender)}")
            mTextViewCreated.text = ("${getString(R.string.character_created)}  ${
                checkData(
                    //substring убирает лишние символы в конце, информационной нагрузки не несут
                    it.created.replace("T", " ").substring(0, it.created.length - 5)
                )
            }")
        })
    }

    //У некоторых персонажей не заполнена часть данных, функция уведомляет об отсутствии данных
    private fun checkData(string: String): String {
        return if (string == "") {
            getString(R.string.no_data_available)
        } else {
            string
        }
    }
}