package com.nacho.marvelsuperheroes.feature_list

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*

fun getFakeHero() = Hero(
    id = 1,
    name = "name",
    description = "dsc",
    modified = "now",
    thumbnail = Thumbnail("", ""),
    resourceURI = "uri",
    etag = "tag",
    comics = Comics(0, "", emptyList(), 1),
    stories = Stories(0, "", emptyList(), 1),
    series = Series(0, "", emptyList(), 1),
    events = Events(0, "", emptyList(), 1),
    urls = emptyList()
)