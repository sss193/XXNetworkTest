package com.example.xxnetworktask

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before

open class MovieTaskBaseTest {
    companion object {
        val testScheduler = TestScheduler()
    }

    @Before
    fun setupRxScheduler() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitSingleSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    @After
    fun afterRxScheduler() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    protected open fun triggerAction() {
        testScheduler.triggerActions()
    }

}