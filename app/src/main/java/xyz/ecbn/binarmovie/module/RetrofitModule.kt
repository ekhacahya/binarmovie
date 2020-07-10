package xyz.ecbn.binarmovie.module

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.ecbn.binarmovie.BuildConfig
import xyz.ecbn.binarmovie.data.ServiceInterface
import java.util.concurrent.TimeUnit

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
val retrofitModule = module {
    single {
        okHttp()
    }
    single {
        retrofit()
    }
    single {
        get<Retrofit>().create(ServiceInterface::class.java)
    }
}

private fun okHttp() = OkHttpClient.Builder()
    .addInterceptor {
        val url = it.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("append_to_response", "videos,images,credits,reviews")
            .build()
        val req = it.request()
            .newBuilder()
            .url(url)
            .build()
        it.proceed(req)
    }
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
    .connectTimeout(60, TimeUnit.SECONDS)
    .build()

private fun retrofit() = Retrofit.Builder()
    .client(okHttp())
    .addConverterFactory(GsonConverterFactory.create())  // 6
    .baseUrl(BuildConfig.BASE_URL)
    .build()
