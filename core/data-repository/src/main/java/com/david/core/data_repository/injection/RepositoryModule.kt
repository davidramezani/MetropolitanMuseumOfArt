package com.david.core.data_repository.injection

import com.david.core.data_repository.repository.GetObjectDetailRepositoryImpl
import com.david.core.data_repository.repository.SearchRepositoryImpl
import com.david.domain.repository.GetObjectDetailRepository
import com.david.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSearchRepository(searchRepository : SearchRepositoryImpl) : SearchRepository

    @Binds
    abstract fun bindGetObjectDetailRepository(getObjectDetailRepository : GetObjectDetailRepositoryImpl) : GetObjectDetailRepository
}