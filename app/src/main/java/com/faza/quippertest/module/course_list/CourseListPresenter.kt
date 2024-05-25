package com.faza.quippertest.module.course_list

import android.app.Activity
import com.faza.quippertest.module.course_list.entity.CourseEntity
import com.faza.quippertest.module.course_list.entity.dummyCourseList

class CourseListPresenter(
    private var view: CourseListInterface.View? = null
) : CourseListInterface.Presenter {
    
    private var interactor: CourseListInterface.Interactor? = null
    private var router: CourseListInterface.Router? = null
    
    override fun onCreate(activity: Activity) {
        view = activity as CourseListActivity
        interactor = CourseListInteractor(activity)
        router = CourseListRouter(activity)

        view?.setupView()
        getCourseList()
    }
    
    override fun onDestroy() {
        view = null
        router = null
    }

    override fun getCourseList() {
        interactor?.getDataCourseList(
            onSuccess = {
                view?.setCourseListData(it)
            }
        )
    }

    override fun goToDetail(data: CourseEntity) {
        router?.goToDetail(data)
    }
}