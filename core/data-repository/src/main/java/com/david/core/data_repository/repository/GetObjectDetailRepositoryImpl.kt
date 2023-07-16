package com.david.core.data_repository.repository

import com.david.core.common.network.Dispatcher
import com.david.core.common.network.MyDispatchers
import com.david.core.data_local.db.detail.DetailDao
import com.david.core.data_local.db.detail.asEntity
import com.david.core.data_local.db.detail.asExternalModel
import com.david.core.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class
GetObjectDetailRepositoryImpl @Inject constructor(
    private val detailDao: DetailDao,
    private val remoteObjectDetailDataSource: RemoteObjectDetailDataSource,
    @Dispatcher(MyDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : GetObjectDetailRepository {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> =

        detailDao.getObjectDetail(objectId).onEach {
            if (it == null) {
                withContext(ioDispatcher) {
                    val museumObject = remoteObjectDetailDataSource.getObjectDetail(objectId)
                    detailDao.insertObjectDetail(museumObject.asEntity())
                }
            }
        }
            .filterNotNull()
            .map {
                it.asExternalModel()
            }


}