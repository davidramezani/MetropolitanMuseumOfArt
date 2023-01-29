package com.david.metropolitanmuseumofart.presentation_detail

import com.david.domain.usecase.GetObjectDetailUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class DetailConverter @Inject constructor() :
    CommonResultConverter<GetObjectDetailUseCase.Response, DetailModel>() {

    override fun convertSuccess(data: GetObjectDetailUseCase.Response): DetailModel {
        val images : MutableList<String> = ArrayList()
        data.museumObject.run {
            images.add(primaryImage)
            images.addAll(additionalImages)
            return DetailModel(
                objectID = objectID,
                primaryImage = primaryImage,
                primaryImageSmall = primaryImageSmall,
                images = images,
                department = department,
                objectName = objectName,
                title = title,
                medium = medium,
                artist = artist,
                artistBio = artistBio
            )
        }
    }
}