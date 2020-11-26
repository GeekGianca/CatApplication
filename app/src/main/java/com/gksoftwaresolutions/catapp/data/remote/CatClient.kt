package com.gksoftwaresolutions.catapp.data.remote

import com.gksoftwaresolutions.catapp.model.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface CatClient {

    @GET("/v1/images/search")
    fun imagesList(@QueryMap params: Map<String, String>): Observable<ImageList>

    @GET("/v1/breeds")
    fun breedsList(@QueryMap params: Map<String, String>): Observable<BreedList>

    @POST("/v1/votes")
    fun createVote(@Body vote: MakeVote): Flowable<ResultVote>

    @GET("/v1/breeds/search")
    fun searchBreed(@Query("q") breed: String) : Single<BreedList>

}