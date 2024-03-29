package com.david.core.data_local.injection

import android.content.Context
import androidx.room.Room
import com.david.core.data_local.db.AppDatabase
import com.david.core.data_local.db.detail.DetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my-database"
        ).build()

    @Provides
    fun provideDetailDao(appDatabase: AppDatabase): DetailDao = appDatabase.detailDao()
}