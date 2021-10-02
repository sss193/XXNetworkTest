package com.example.xxnetworktask.di

import com.example.xxnetworktask.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface MovieTaskComponent {
        fun inject(activity: MainActivity)
}