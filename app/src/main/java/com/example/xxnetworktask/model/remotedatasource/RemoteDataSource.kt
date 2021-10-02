package com.example.xxnetworktask.model.remotedatasource


import com.example.xxnetworktask.common.AppConstant
import com.example.xxnetworktask.model.datamodel.MovieDetailsDataModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource {

    val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val movieTaskApi = retrofit.create(MovieTaskApi::class.java)

    fun getMovieDetails(id: Int): Single<MovieDetailsDataModel> {
        return movieTaskApi.getMovieDetails(id, AppConstant.API_KEY, AppConstant.LANGUAGE_CODE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
