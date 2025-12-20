package com.example.shoe_shop.data

import com.example.myfirstproject.data.service.UserManagementService
import com.example.shoe_shop.data.service.CategoriesService
import com.example.shoe_shop.data.service.ProductsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object RetrofitInstance {
    const val SUPABASE_URL = "https://ywmcbevoezswgjyevxxe.supabase.co"
    const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl3bWNiZXZvZXpzd2dqeWV2eHhlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU5NDgwODQsImV4cCI6MjA4MTUyNDA4NH0.h_bZx40ymD-DK2MpSki8P5HrSfgWVRhPezXaWCrKmHo"

    // Конфигурация прокси
    private val proxy: Proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("10.207.106.71", 3128))

    // 1. Клиент для Auth API (только apikey)
    private fun createAuthClient(): OkHttpClient {
        return createBaseClient().newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // 2. Клиент для REST API (apikey + Authorization)
    private fun createRestApiClient(): OkHttpClient {
        return createBaseClient().newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", API_KEY)
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "return=representation")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    // 3. Базовый клиент с SSL bypass
    private fun createBaseClient(): OkHttpClient {
        try {
            // Trust manager для обхода SSL
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            return OkHttpClient.Builder()
                .proxy(proxy)
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        } catch (e: Exception) {
            throw RuntimeException("Failed to create OkHttpClient", e)
        }
    }

    // 4. Два разных Retrofit инстанса
    private val authRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createAuthClient())
            .build()
    }

    private val restApiRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createRestApiClient())
            .build()
    }

    // 5. Сервисы используют разные клиенты
    val userManagementService: UserManagementService by lazy {
        authRetrofit.create(UserManagementService::class.java)
    }

    val productsService: ProductsService by lazy {
        restApiRetrofit.create(ProductsService::class.java)
    }

    val categoriesService: CategoriesService by lazy {
        restApiRetrofit.create(CategoriesService::class.java)
    }
}