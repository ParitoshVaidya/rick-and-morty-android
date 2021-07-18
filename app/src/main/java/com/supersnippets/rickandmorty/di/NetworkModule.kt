package com.supersnippets.rickandmorty.di

import android.content.Context
import com.supersnippets.rickandmorty.helpers.ApiService
import com.supersnippets.rickandmorty.helpers.BASE_URL
import com.supersnippets.rickandmorty.helpers.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideNetInterceptor(androidContext()) }
    single { provideOkHttpClient(get()) }
    single { provideApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    println("generated retrofit")
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNetInterceptor(context: Context): NetworkInterceptor {
    return NetworkInterceptor(context)
}

fun provideOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    println("generated OkHttpClient")
    return OkHttpClient()
        .newBuilder()
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(networkInterceptor)
        .addInterceptor(logging)
        .build()
}

fun provideApi(retrofit: Retrofit): ApiService {
    println("generated ApiService")
    return retrofit.create(ApiService::class.java)
}
