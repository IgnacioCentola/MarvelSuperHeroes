package com.nacho.marvelsuperheroes.feature_list.data.remote

data class HeroResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val status: String,
    val data : QueryData
)