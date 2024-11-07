package com.cinetech.messenger.di

import com.cinetech.data.remote.RegistrationService
import com.cinetech.data.repository.RegistrationRepositoryImpl
import com.cinetech.domain.repository.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class RegistrationModule {

    @Provides
    @ViewModelScoped
    fun provideRegistrationService(retrofit: Retrofit): RegistrationService = retrofit.create(RegistrationService::class.java)

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(registrationService: RegistrationService): RegistrationRepository = RegistrationRepositoryImpl(registrationService = registrationService)
}