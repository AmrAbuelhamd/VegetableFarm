package com.blogspot.soyamr.vegetablefarm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blogspot.soyamr.vegetablefarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: CellViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this


    }
}