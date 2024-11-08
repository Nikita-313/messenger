package com.cinetech.messenger.di

import com.cinetech.data.remote.AuthAuthenticator
import com.cinetech.data.remote.RefreshJwtService
import com.cinetech.data.remote.interceptor.AccessTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    @Provides
    @Singleton
    @NotAuthenticatedClient
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRefreshJwtService(@NotAuthenticatedClient retrofit: Retrofit): RefreshJwtService {
        return retrofit.create(RefreshJwtService::class.java)
    }


    companion object {
        const val BASE_URL = "https://plannerok.ru"
    }
}