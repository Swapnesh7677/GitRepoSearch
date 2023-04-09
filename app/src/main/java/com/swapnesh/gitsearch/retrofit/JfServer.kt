package com.swapnesh.gitsearch.retrofit



import com.swapnesh.gitsearch.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object JfServer {

    private var retrofit: Retrofit? = null

    fun getJfServer(): Retrofit {
        if (retrofit == null) {

            val httpClient = OkHttpClient().newBuilder()
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.readTimeout(60, TimeUnit.SECONDS)
            httpClient.writeTimeout(60, TimeUnit.SECONDS)

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        }


        return retrofit!!
    }

}