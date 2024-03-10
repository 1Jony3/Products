package com.example.products.model.di

import com.example.products.model.data.repository.AppRepository
import com.example.products.model.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindRepository(
        repository: AppRepository
    ):Repository
}