package com.aryan.animeexplorer.domain.model

import com.aryan.AnimeDetailsQuery
import com.aryan.type.MediaFormat
import com.aryan.type.MediaStatus

data class AnimeDetails(
     val id: Int,
     val title: String?=null,
     val romanjiTitle:String?=null,
     val description: String?=null,
     val bannerImage: String?=null,
     val imageUrl:String?=null,
     val color:String?=null,
     val format: String?=null,
     val episodes: Int?=null,
     val duration: Int?=null,
     val status: String?=null,
     val startYear: String?=null,
     val meanScore: Int?=null,
     val genres: String?=null,
){

}