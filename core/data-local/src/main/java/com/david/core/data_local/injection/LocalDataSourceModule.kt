package com.david.core.data_local.injection

import com.david.core.data_local.source.LocalObjectDetailDataSourceImpl
import com.david.core.data_repository.data_source.local.LocalObjectDetailDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindObjectDetailDataSource(objectDetailDataSourceImpl: LocalObjectDetailDataSourceImpl): LocalObjectDetailDataSource
}