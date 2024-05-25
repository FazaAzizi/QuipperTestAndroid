package com.faza.quippertest.module.course_list.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CourseEntity(
    val title: String,

    @SerializedName("presenter_name")
    val presenterName: String,

    val description: String,

    @SerializedName("thumbnail_url")
    val thumbnailURL: String,

    @SerializedName("video_url")
    val videoURL: String,

    @SerializedName("video_duration")
    val videoDuration: Long
) : Parcelable