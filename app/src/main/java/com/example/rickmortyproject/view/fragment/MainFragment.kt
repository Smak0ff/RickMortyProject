package com.example.rickmortyproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyproject.R
import com.example.rickmortyproject.databinding.FragmentMainBinding
import com.example.rickmortyproject.model.Results
import com.example.rickmortyproject.utils.CHARACTER_ID
import com.example.rickmortyproject.view.recyclerview.CharacterRecyclerView
import com.example.rickmortyproject.viewmodel.MainFragmentViewModel

class MainFragment : Fragment(R.layout.fragment_main), CharacterRecyclerView.Listener {
    private lateinit var mBinding: FragmentMainBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: CharacterRecyclerView
    private lateinit var mViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Инициализация ViewBinding
        mBinding = FragmentMainBinding.inflate(layoutInflater)
        //Инициализация RecyclerView
        mRecyclerView = mBinding.recyclerViewId
        mRecyclerView.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        //Инициализация ViewModel
        mViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        //Слушатель на получение списка персонажей
        mViewModel.liveData.observe(this, Observer {
            mAdapter = CharacterRecyclerView(this, it)
            mRecyclerView.adapter = mAdapter
        })
    }

    //Кнопка для перехода на фрагмент детального просмотра
    override fun onButtonImageClick(result: Results) {
        super.onButtonImageClick(result)
        CHARACTER_ID = result.id
        this.fragmentManager?.beginTransaction()?.replace(R.id.fragmentWindowId, DetailsFragment())
            ?.addToBackStack(null)?.commit()
    }
}