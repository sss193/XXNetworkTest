package com.example.xxnetworktask.di

import com.example.xxnetworktask.presentation.view.HomeActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface MovieTaskComponent {
    fun inject(activity: HomeActivity)
}