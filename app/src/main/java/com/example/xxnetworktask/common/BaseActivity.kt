package com.example.xxnetworktask.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xxnetworktask.R
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {

    val globalDisposable = CompositeDisposable()


    override fun onDestroy() {
        globalDisposable.run {
            if (!isDisposed) dispose()
            clear()
        }
        super.onDestroy()
    }
}