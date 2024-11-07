package com.cinetech.messenger.di

import com.cinetech.data.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class UserModule {
    @Provides
    fun provideUserService(retrofit: Retrofit):UserService {
        return retrofit.create(UserService::class.java)
    }
}