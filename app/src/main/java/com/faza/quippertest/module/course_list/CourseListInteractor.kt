package com.faza.quippertest.module.course_list

import android.app.Activity
import com.faza.quippertest.module.course_list.entity.CourseEntity
import com.faza.quippertest.services.Sessions
import com.faza.quippertest.services.NetworkBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CourseListInteractor(activity: Activity) : CourseListInterface.Interactor {
    
    private val disposable = CompositeDisposable()
    private val apiService = NetworkBuilder.apiService
    private val sessions = Sessions(activity)
    override fun getDataCourseList(onSuccess: (List<CourseEntity>) -> Unit) {
        apiService.getCourseList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess)
            .let(disposable::add)
    }
}