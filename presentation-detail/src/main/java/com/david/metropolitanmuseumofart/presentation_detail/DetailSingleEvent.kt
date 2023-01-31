package com.david.metropolitanmuseumofart.presentation_detail

import com.david.metropolitanmuseumofart.presentation_common.state.UiSingleEvent

sealed class DetailSingleEvent : UiSingleEvent {

    object CloseDetailFragment : UiSingleEvent
}