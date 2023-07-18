package com.david.core.data_local.db.detail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {

    @Query("SELECT * FROM museum_object WHERE objectID=:objectId")
    fun getObjectDetail(objectId: Int): Flow<MuseumObjectEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObjectDetail(museumObject: MuseumObjectEntity)
}