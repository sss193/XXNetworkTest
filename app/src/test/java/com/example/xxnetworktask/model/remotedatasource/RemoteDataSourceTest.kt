package com.example.xxnetworktask.model.remotedatasource

import com.example.xxnetworktask.MovieTaskBaseTest
import com.example.xxnetworktask.model.datamodel.MovieDetailsResponse
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest : MovieTaskBaseTest() {


    @Mock
    lateinit var mockMovieTaskApi: MovieTaskApi

    lateinit var remoteDataSource: IRemoteDataSource

    private val testId = 0
    private val testTitle = "testMovie"
    private val testDes = "testDes"
    private val testPoster = "testUrl"

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(mockMovieTaskApi, "", "")
    }

    val movieDetailsResponse = MovieDetailsResponse(testId, testTitle, testPoster, testDes, null)

    @Test
    fun getMovieDetails_returnSuccess() {
        Mockito.`when`(mockMovieTaskApi.getMovieDetails(0, "", "")).thenReturn(
            Single.just(movieDetailsResponse)
        )

        val testObserver = TestObserver.create<MovieDetailsResponse>()
        remoteDataSource.getMovieDetails(0).subscribe(testObserver)
        triggerAction()
        testObserver.assertNoErrors()
        testObserver.assertValue {
            it._id == testId
            it._title == testTitle
            it._description == testDes
            it._poster == testPoster

        }
        Mockito.verify(mockMovieTaskApi, Mockito.times(1))
            .getMovieDetails(0, "", "")
    }

    @Test
    fun getMovieDetails_returnError() {
        Mockito.`when`(mockMovieTaskApi.getMovieDetails(0, "", "")).thenReturn(
            Single.error(Throwable())
        )

        val testObserver = TestObserver.create<MovieDetailsResponse>()
        remoteDataSource.getMovieDetails(0).subscribe(testObserver)
        triggerAction()
        testObserver.assertError(Throwable::class.java)

        Mockito.verify(mockMovieTaskApi, Mockito.times(1))
            .getMovieDetails(0, "", "")
    }

}