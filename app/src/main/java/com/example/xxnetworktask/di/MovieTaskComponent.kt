package com.example.xxnetworktask.di

import com.example.xxnetworktask.presentation.view.HomeActivity
import dagger.Component

@Component(modules = [NetworkModule::class, MovieTaskModule::class])
interface MovieTaskComponent {
    fun inject(activity: HomeActivity)
}