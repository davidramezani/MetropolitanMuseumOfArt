package com.david.core.data_remote.networking.detail

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getObjectDetail(@Path("objectID") objectId: Int): MuseumObjectApiModel
}