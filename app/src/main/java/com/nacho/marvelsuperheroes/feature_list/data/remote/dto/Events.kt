package com.nacho.marvelsuperheroes.feature_list.data.remote.dto

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Event

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Event>,
    val returned: Int
)