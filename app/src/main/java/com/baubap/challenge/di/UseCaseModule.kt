package com.baubap.challenge.di

import com.baubap.challenge.domain.repository.AuthRepository
import com.baubap.challenge.domain.usecase.LoginUseCase
import com.baubap.challenge.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(repo: AuthRepository) = LoginUseCase(repo)
    @Provides
    fun provideRegisterUseCase(repo: AuthRepository) = RegisterUseCase(repo)
}