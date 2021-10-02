package com.example.xxnetworktask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xxnetworktask.di.DaggerMovieTaskComponent
import com.example.xxnetworktask.di.NetworkModule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDagger()
    }

    private fun initializeDagger() {
       DaggerMovieTaskComponent.builder()
           .networkModule(NetworkModule("test"))
           .build()
           .inject(this)
    }
}