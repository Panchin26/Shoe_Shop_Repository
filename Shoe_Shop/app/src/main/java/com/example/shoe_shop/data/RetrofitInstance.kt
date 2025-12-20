package com.example.shoe_shop.data

import com.example.myfirstproject.data.service.UserManagementService
import com.example.shoe_shop.data.service.CategoriesService
import com.example.shoe_shop.data.service.ProductsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object RetrofitInstance {
    // Базовый URL для всех сервисов Supabase
    const val SUPABASE_URL = "https://ywmcbevoezswgjyevxxe.supabase.co"
    const val REST_URL = "$SUPABASE_URL/rest/v1/"

    // Прокси настройки
    var proxy: Proxy = Proxy(Proxy.Type.HTTP,  InetSocketAddress(  "10.207.106.71",  3128))
    // Основной клиент для всех запросов
    var client: OkHttpClient = OkHttpClient.Builder().proxy(proxy)
        .addInterceptor { chain ->
            val original = chain.request()

            // Добавляем обязательные заголовки для Supabase REST API
            val requestBuilder = original.newBuilder()
                .header("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl3bWNiZXZvZXpzd2dqeWV2eHhlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU5NDgwODQsImV4cCI6MjA4MTUyNDA4NH0.h_bZx40ymD-DK2MpSki8P5HrSfgWVRhPezXaWCrKmHo") // Замените на ваш ключ!
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl3bWNiZXZvZXpzd2dqeWV2eHhlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU5NDgwODQsImV4cCI6MjA4MTUyNDA4NH0.h_bZx40ymD-DK2MpSki8P5HrSfgWVRhPezXaWCrKmHo") // Замените на ваш ключ!
                .header("Content-Type", "application/json")
                .method(original.method, original.body)

            // Для авторизации могут быть другие заголовки
            val url = original.url.toString()
            if (url.contains("/auth/")) {
                // Для auth endpoints используем только apikey
                requestBuilder.removeHeader("Authorization")
                requestBuilder.header("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl3bWNiZXZvZXpzd2dqeWV2eHhlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU5NDgwODQsImV4cCI6MjA4MTUyNDA4NH0.h_bZx40ymD-DK2MpSki8P5HrSfgWVRhPezXaWCrKmHo") // Тот же ключ
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Retrofit для авторизации (использует основной URL)
    private val retrofitAuth = Retrofit.Builder()
        .baseUrl(SUPABASE_URL) // Базовый URL для auth
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    // Retrofit для товаров и категорий (использует REST URL)
    private val retrofitRest = Retrofit.Builder()
        .baseUrl(REST_URL) // Базовый URL для REST API
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    // Сервисы
    val userManagementService = retrofitAuth.create(UserManagementService::class.java)
    val productsService = retrofitRest.create(ProductsService::class.java)
    val categoriesService = retrofitRest.create(CategoriesService::class.java)
}