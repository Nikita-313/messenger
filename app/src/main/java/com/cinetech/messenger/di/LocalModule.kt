package com.cinetech.messenger.di

import android.content.Context
import androidx.room.Room
import com.cinetech.data.local.AppDatabase
import com.cinetech.data.repository.JwtTokenRepositoryImpl
import com.cinetech.data.repository.UserLocalRepositoryImpl
import com.cinetech.domain.repository.JwtTokenRepository
import com.cinetech.domain.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MyMessengerDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideJwtTokenRepository(appDatabase: AppDatabase): JwtTokenRepository = JwtTokenRepositoryImpl(appDatabase.jwtTokenDao())

    @Provides
    fun provideUserLocalRepository(appDatabase: AppDatabase): UserLocalRepository = UserLocalRepositoryImpl(appDatabase.userDao())
}