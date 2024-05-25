package com.faza.quippertest.services

import com.faza.quippertest.module.course_list.entity.CourseEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("native-technical-exam/playlist.json")
    fun getCourseList(): Single<List<CourseEntity>>
}