package com.faza.quippertest.module.course_detail

import android.app.Activity

class CourseDetailRouter(private val activity: Activity) : CourseDetailInterface.Router {
    override fun goToList() {
        activity.finish()
    }
}