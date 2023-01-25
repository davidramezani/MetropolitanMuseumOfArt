package com.david.data_remote.injection

import com.david.data_remote.networking.detail.DetailService
import com.david.data_remote.networking.search.SearchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideJsonNetwork(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, networkJson: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1")
            .client(okHttpClient)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    fun provideDetailService(retrofit: Retrofit): DetailService =
        retrofit.create(DetailService::class.java)
}