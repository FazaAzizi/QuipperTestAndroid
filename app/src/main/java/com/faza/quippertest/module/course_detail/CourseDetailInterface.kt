package com.faza.quippertest.module.course_detail

import android.app.Activity
import com.faza.quippertest.module.course_list.entity.CourseEntity

interface CourseDetailInterface {
    interface View {
        fun setDetailData(data: CourseEntity)
    }
    
    interface Presenter {
        fun onCreate(activity: Activity)
        fun onDestroy()
        fun goToList()
    }
    
    interface Interactor {
        
    }
    
    interface Router {
        fun goToList()
    }
}