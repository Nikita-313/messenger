package com.cinetech.messenger.di

import com.cinetech.data.remote.AuthService
import com.cinetech.data.repository.AuthRepositoryImpl
import com.cinetech.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit


@InstallIn(ViewModelComponent::class)
@Module
class AuthModule {

    @Provides
    @ViewModelScoped
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)


    @Provides
    @ViewModelScoped
    fun provideAuthRepository(authService: AuthService): AuthRepository = AuthRepositoryImpl(authService = authService)

}