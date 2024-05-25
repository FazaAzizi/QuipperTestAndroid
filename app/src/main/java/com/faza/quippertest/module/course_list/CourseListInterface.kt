package com.faza.quippertest.module.course_list

import android.app.Activity
import com.faza.quippertest.module.course_list.entity.CourseEntity

interface CourseListInterface {
    interface View {
        fun setupView()

        fun setCourseListData(data: List<CourseEntity>)
    }
    
    interface Presenter {
        fun onCreate(activity: Activity)
        fun onDestroy()
        fun getCourseList()
        fun goToDetail(data: CourseEntity)
    }
    
    interface Interactor {
        fun getDataCourseList(
            onSuccess: (List<CourseEntity>) -> Unit
        )
    }
    
    interface Router {
        fun goToDetail(data: CourseEntity)
    }
}