package com.faza.quippertest.module.course_list

import android.app.Activity
import com.faza.quippertest.module.course_list.entity.CourseEntity
import com.faza.quippertest.services.Sessions
import com.faza.quippertest.services.NetworkBuilder
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CourseListInteractor(activity: Activity) : CourseListInterface.Interactor {
    
    private val disposable = CompositeDisposable()
    private val apiService = NetworkBuilder.apiService
    private val sessions = Sessions(activity)
    override fun getDataCourseList(onSuccess: (List<CourseEntity>) -> Unit) {

    }
}