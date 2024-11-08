package com.cinetech.messenger.di

import com.cinetech.data.remote.UserService
import com.cinetech.data.repository.UserRepositoryImpl
import com.cinetech.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class UserModule {

    @Provides
    @ViewModelScoped
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideUserRepository(userService: UserService): UserRepository = UserRepositoryImpl(userService = userService)

}