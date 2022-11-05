package com.bintangpoetra.sumbanginaja.di

import com.bintangpoetra.sumbanginaja.BuildConfig
import com.bintangpoetra.sumbanginaja.data.auth.remote.AuthService
import com.bintangpoetra.sumbanginaja.data.food.remote.FoodService
import com.bintangpoetra.sumbanginaja.data.lib.HeaderInterceptor
import com.bintangpoetra.sumbanginaja.data.region.remote.RegionService
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { provideFoodService(get()) }

    single { provideAuthService(get()) }

    single { provideRegionService(get()) }

}

private fun getHeaderInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    //define default headers here
    headers["Content-Type"] = "application/json"

    return HeaderInterceptor(headers, preferenceManager)
}

fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

fun provideFoodService(retrofit: Retrofit): FoodService = retrofit.create(FoodService::class.java)

fun provideRegionService(retrofit: Retrofit): RegionService = retrofit.create(RegionService::class.java)