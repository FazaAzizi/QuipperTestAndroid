package com.faza.quippertest.module.course_detail

import android.app.Activity
import android.os.Build
import com.faza.quippertest.module.course_list.entity.CourseEntity

class CourseDetailPresenter(
    private var view: CourseDetailInterface.View? = null
) : CourseDetailInterface.Presenter {

    private var data: CourseEntity? = null

    private var interactor: CourseDetailInterface.Interactor? = null
    private var router: CourseDetailInterface.Router? = null
    
    override fun onCreate(activity: Activity) {
        view = activity as CourseDetailActivity
        interactor = CourseDetailInteractor(activity)
        router = CourseDetailRouter(activity)

        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.intent?.getParcelableExtra("Data", CourseEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            activity.intent?.getParcelableExtra("Data")
        } ?: throw IllegalArgumentException("No data passed")

        data?.let { view?.setDetailData(it) }
    }
    
    override fun onDestroy() {
        view = null
        router = null
    }

    override fun goToList() {
        router?.goToList()
    }
}