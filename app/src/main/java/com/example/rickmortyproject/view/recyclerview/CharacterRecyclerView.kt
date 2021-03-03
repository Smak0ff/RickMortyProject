package com.example.rickmortyproject.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyproject.R
import com.example.rickmortyproject.databinding.RecyclerCharacterItemBinding
import com.example.rickmortyproject.model.Results

class CharacterRecyclerView(private val listener: Listener, var mListCharacter: List<Results>) :
    RecyclerView.Adapter<CharacterRecyclerView.CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_character_item, parent, false)
        return CharacterViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        var character = mListCharacter[position]
        if (character != null) {
            holder.bind(character)
        }
    }

    override fun getItemCount(): Int {
        return mListCharacter.size
    }

    class CharacterViewHolder(private val listener: Listener, view: View) :
        RecyclerView.ViewHolder(view) {
        private var mBinding = RecyclerCharacterItemBinding.bind(view)
        private var mImageView = mBinding.imageForRecyclerId
        private var mTextView = mBinding.nameForRecyclerId
        private var character: Results? = null

        //Убираю логику
        init {
            //Слушатель для кнопки на айтеме
            view.setOnClickListener {
                character?.let {
                    listener.onButtonImageClick(it)
                }
            }
        }

        fun bind(character: Results) {
            this.character = character
            mTextView.text = character.name
            Glide.with(mImageView.context)
                .load(character.image)
                .into(mImageView)
        }
    }

    //Интерфейс для кнопки на айтеме
    interface Listener {
        fun onButtonImageClick(result: Results) {
        }
    }
}