package ru.lorens.itunesapi.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lorens.itunesapi.BASE_URL

object RestClient {

    val getClient: ApiInterface by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("search?entity=album&attribute=albumTerm")
        suspend fun getAlbumsByName(@Query("term") term: String): ResultAlbum

        @GET("lookup?entity=song")
        suspend fun getSongsByAlbumId(@Query("id") id: Long): ResultSong
    }

}