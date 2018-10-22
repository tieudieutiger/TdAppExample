package com.tieudieu.appexample.core.di.module

import android.app.Application
import android.os.Build
import com.cis.iparkingpayment.core.utils.restapi.ApiConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    private var mBaseUrl: String? = null

    constructor(baseUrl: String?) {
        this.mBaseUrl = baseUrl
    }

    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig = ApiConfig()

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {

        val cacheSize: Long = 10 * 1024 * 1024

        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {

        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiConfig: ApiConfig): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()

        interceptor.level = if (apiConfig.isDebuggable()) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        if (Build.VERSION.SDK_INT >= 24 && !apiConfig.isApiTest()) {
            val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .tlsVersions(TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                    )
                    .build()
            return OkHttpClient.Builder()
                    .connectionSpecs(Collections.singletonList(spec))
                    .addInterceptor(interceptor)
                    .addInterceptor { chain ->
                        val original: Request = chain!!.request()
                        var request: Request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("User-Agent", "Private Parking")
                                .header("Authorization", apiConfig.getToken())
                                .method(original.method(), original.body())
                                .build()
                        val response: Response = chain.proceed(request)
                        response
                    }
                    .connectTimeout(24, TimeUnit.SECONDS)
                    .build()
        } else {
            return OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor { chain ->
                        val original: Request = chain!!.request()
                        val request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Accept", "Private Parking")
                                .header("Authorization", apiConfig.getToken())
                                .method(original.method(), original.body())
                                .build()

                        val response: Response = chain.proceed(request)
                        response
                    }
                    .connectTimeout(24, TimeUnit.SECONDS)
                    .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }

    /*@Provides
    @Singleton
    fun provideApiRouter(retrofit: Retrofit): ApiRouter = retrofit.create(ApiRouter::class.java)

    @Provides
    @Singleton
    fun provideRetrofitService(apiConfig: ApiConfig, apiRouter: ApiRouter): ApiService
            = ApiService(apiConfig, apiRouter)*/

}