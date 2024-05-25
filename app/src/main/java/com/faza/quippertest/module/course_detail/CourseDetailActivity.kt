package com.faza.quippertest.module.course_detail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.faza.quippertest.databinding.ActivityCourseDetailBinding
import com.faza.quippertest.module.course_list.entity.CourseEntity
import com.faza.quippertest.utils.Utils.setImageViewUrl

class CourseDetailActivity : AppCompatActivity(), CourseDetailInterface.View {
    
    private val presenter: CourseDetailPresenter = CourseDetailPresenter(this)
    private val binding: ActivityCourseDetailBinding by lazy {
        ActivityCourseDetailBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter.onCreate(this)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setDetailData(data: CourseEntity) = with(binding) {
        ivArrowBack.setOnClickListener{
            presenter.goToList()
        }

        vwCourseDetail.setVideoURI(Uri.parse(data.videoURL))
        ivTumbnailCourseDetail.setImageViewUrl(data.thumbnailURL)
        tvPresenterCourseDetail.text = data.presenterName
        tvTitleCourseDetail.text = data.title

        val durationInSeconds = data.videoDuration / 1000
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60
        tvDurationDetailCourse.text = "Duration: ${minutes}m ${seconds}s"

        tvDescCourseDetail.text = data.description

        val mediaController = MediaController(root.context)
        mediaController.setAnchorView(vwCourseDetail)
        vwCourseDetail.setMediaController(mediaController)
    }
}
