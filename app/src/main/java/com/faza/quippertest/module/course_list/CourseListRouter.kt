package com.faza.quippertest.module.course_list

import android.app.Activity
import android.content.Intent
import com.faza.quippertest.module.course_detail.CourseDetailActivity
import com.faza.quippertest.module.course_list.entity.CourseEntity

class CourseListRouter(private val activity: Activity) : CourseListInterface.Router {
    override fun goToDetail(data: CourseEntity) = with(activity) {
        startActivity(
            Intent(this, CourseDetailActivity::class.java).apply {
                putExtra("Data", data)
            }
        )
    }
}