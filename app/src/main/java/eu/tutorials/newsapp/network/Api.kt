package eu.tutorials.newsapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private val BASE_URL = "https://newsapi.org/v2/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    //Todo 6 : create a loggin variable
    val logging = HttpLoggingInterceptor()

    //Todo 2: setup Okhttp client with a the api key in the header
    val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{chain->
           val builder = chain.request().newBuilder()
               builder.header("X-Api-Key","0068ac69d80f4a97b794fa26311cb323")
               return@Interceptor chain.proceed(builder.build())
            }
        )
        //Todo 7: set up the log to show even the body of the response
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()


    //Todo 3: add httpclient to the retrofit
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}