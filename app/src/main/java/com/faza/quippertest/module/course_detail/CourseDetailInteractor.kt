package com.faza.quippertest.module.course_detail

import android.app.Activity
import com.faza.quippertest.services.Sessions
import com.faza.quippertest.services.NetworkBuilder
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CourseDetailInteractor(activity: Activity) : CourseDetailInterface.Interactor {
    
    private val disposable = CompositeDisposable()
    private val apiService = NetworkBuilder.apiService
    private val sessions = Sessions(activity)
}