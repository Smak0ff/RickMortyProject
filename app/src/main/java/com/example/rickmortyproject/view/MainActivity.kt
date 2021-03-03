package com.example.rickmortyproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickmortyproject.R
import com.example.rickmortyproject.databinding.ActivityMainBinding
import com.example.rickmortyproject.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //Программа с одним активити, переключение происходит между фрагментами.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentWindowId, MainFragment()).commit()
    }
}