package com.david.data_remote.networking.search

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/search")
    suspend fun searchObjects(@Query("q") searchQuery : String) : SearchApiModel
}