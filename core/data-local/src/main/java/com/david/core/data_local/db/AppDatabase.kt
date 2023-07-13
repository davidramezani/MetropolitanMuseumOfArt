package com.david.core.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.david.core.data_local.db.detail.DetailDao
import com.david.core.data_local.db.detail.MuseumObjectEntity

@Database(entities = [MuseumObjectEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun detailDao(): DetailDao
}