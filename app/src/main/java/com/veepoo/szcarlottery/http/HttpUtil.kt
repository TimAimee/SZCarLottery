package com.veepoo.szcarlottery.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object HttpUtil {
    fun retrofit(): HttpService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val build = getUnsafeOkHttpClient().build()
        // Log信息拦截器
        val retrofit = Retrofit.Builder()
            .baseUrl(HttpService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(build)
            .build()
        //ApiStores::class.java取得对象的 Java 类
        return retrofit.create(HttpService::class.java)
    }

    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            var singleTrust = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    val arrayOf = arrayOf<java.security.cert.X509Certificate>()
                    return arrayOf
                }
            }

            val trustAllCerts = arrayOf<TrustManager>(singleTrust)
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor)
            builder.connectTimeout(30L, TimeUnit.SECONDS)
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }



}

