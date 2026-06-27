package com.example.rateflow.di


import com.example.rateflow.data.remote.tcmb.TcmbService
import com.example.rateflow.data.remote.tcmb.TcmbXmlParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }



    @Provides
    @Singleton
    fun provideTcmbXmlParser(): TcmbXmlParser = TcmbXmlParser()

    @Provides
    @Singleton
    fun provideTcmbService(client: OkHttpClient, parser: TcmbXmlParser): TcmbService {
        return TcmbService(client, parser)
    }
}
