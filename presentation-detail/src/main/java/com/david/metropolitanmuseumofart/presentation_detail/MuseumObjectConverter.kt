package com.david.metropolitanmuseumofart.presentation_detail

import com.david.domain.usecase.GetObjectDetailUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class MuseumObjectConverter @Inject constructor() :
    CommonResultConverter<GetObjectDetailUseCase.Response, MuseumObjectModel>() {

    override fun convertSuccess(data: GetObjectDetailUseCase.Response): MuseumObjectModel {
        data.museumObject.run {
            return MuseumObjectModel(
                objectID = objectID,
                primaryImage = primaryImage,
                primaryImageSmall = primaryImageSmall,
                additionalImages = additionalImages,
                department = department,
                objectName = objectName,
                title = "",
                medium = "",
                artist = "",
                artistBio = ""
            )
        }
    }
}