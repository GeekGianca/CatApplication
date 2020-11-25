package com.gksoftwaresolutions.catapp.data.remote

import com.gksoftwaresolutions.catapp.model.BreedList
import com.gksoftwaresolutions.catapp.model.ImageList
import com.gksoftwaresolutions.catapp.model.MakeVote
import com.gksoftwaresolutions.catapp.model.ResultVote
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface CatClient {

    @GET("/images/search")
    fun imagesList(@QueryMap params: Map<String, String>): Observable<ImageList>

    @GET("/breeds")
    fun breedsList(@QueryMap params: Map<String, String>): Observable<BreedList>

    @POST("/votes")
    fun createVote(@Body vote: MakeVote): Flowable<ResultVote>

}