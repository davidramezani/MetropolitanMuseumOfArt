package com.david.data_remote.injection

import com.david.data_remote.source.RemoteObjectDetailDataSourceImpl
import com.david.data_remote.source.RemoteSearchObjectsDataSourceImpl
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindSearchObjectsDataSource(searchObjectsDataSourceImpl: RemoteSearchObjectsDataSourceImpl): RemoteSearchObjectsDataSource

    @Binds
    abstract fun bindDetailObjectDataSource(objectDetailDataSourceImpl: RemoteObjectDetailDataSourceImpl): RemoteObjectDetailDataSource
}