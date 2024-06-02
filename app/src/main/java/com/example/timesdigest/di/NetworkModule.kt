package com.example.timesdigest.di

import com.example.timesdigest.BuildConfig
import com.example.timesdigest.data.network.api.ApiKeyInterceptor
import com.example.timesdigest.data.network.api.NYTimesService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(networkJson: Json, client: dagger.Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .callFactory { client.get().newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): NYTimesService {
        return retrofit.create(NYTimesService::class.java)
    }
}