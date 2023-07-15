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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class
GetObjectDetailRepositoryImpl @Inject constructor(
    private val detailDao: DetailDao,
    private val remoteObjectDetailDataSource: RemoteObjectDetailDataSource,
    @Dispatcher(MyDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : GetObjectDetailRepository {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> =
        /*flow {
            emit(
                MuseumObject(
                    1,
                    "",
                    "",
                    emptyList(),
                    "asdasd",
                    "asdasd",
                    "asdasd",
                    "asdasdasd",
                    "ergerg",
                    "hkdfgjhlkfgjh"
                )
            )
        }*/
        /*flow<MuseumObject> {
            withContext(ioDispatcher) {
                remoteObjectDetailDataSource.getObjectDetail(objectId)
            }
        }.onEach {
            detailDao.insertObjectDetail(it.asEntity())
        }.onStart {
           detailDao.getObjectDetail(objectId)
        }*/

        /*detailDao.getObjectDetail(objectId).map {
            it.asExternalModel()
        }.onStart {
            withContext(ioDispatcher) {
                val result = remoteObjectDetailDataSource.getObjectDetail(objectId)
                detailDao.insertObjectDetail(result.asEntity())
            }
        }.onEmpty {
            withContext(ioDispatcher) {
                val result = remoteObjectDetailDataSource.getObjectDetail(objectId)
                detailDao.insertObjectDetail(result.asEntity())
            }
        }*/

        //localObjectDetailDataSource.getObjectDetail(objectId)

        detailDao.getObjectDetail(objectId).onEach {
            if (it == null) {
                withContext(ioDispatcher) {
                    val museumObject = remoteObjectDetailDataSource.getObjectDetail(objectId)
                    detailDao.insertObjectDetail(museumObject.asEntity())
                }
            }
        }.map {
            it.asExternalModel()
        }.filterNotNull()

}