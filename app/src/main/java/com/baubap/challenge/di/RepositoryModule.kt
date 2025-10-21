package com.baubap.challenge.di

import com.baubap.challenge.data.remote.AuthRemoteDataSource
import com.baubap.challenge.data.repository.AuthRepositoryImpl
import com.baubap.challenge.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(ds: AuthRemoteDataSource): AuthRepository =
        AuthRepositoryImpl(ds)
}