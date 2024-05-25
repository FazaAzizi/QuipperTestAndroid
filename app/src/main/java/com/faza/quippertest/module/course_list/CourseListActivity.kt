package com.faza.quippertest.module.course_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faza.quippertest.databinding.ActivityCourseListBinding
import com.faza.quippertest.module.course_list.adapter.RecyclerViewCourseListAdapter
import com.faza.quippertest.module.course_list.entity.CourseEntity

class CourseListActivity : AppCompatActivity(), CourseListInterface.View, RecyclerViewCourseListAdapter.OnItemClickListener {
    
    private val presenter: CourseListPresenter = CourseListPresenter(this)
    private val binding: ActivityCourseListBinding by lazy {
        ActivityCourseListBinding.inflate(layoutInflater)
    }

    private val courseListAdapter: RecyclerViewCourseListAdapter by lazy {
        RecyclerViewCourseListAdapter()
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

    override fun setupView() {
        setupCourseListAdapter()
    }

    override fun setCourseListData(data: List<CourseEntity>) {
        courseListAdapter.setCourseList(data)
    }

    private fun setupCourseListAdapter() {
        with(binding) {
            rvCourseList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@CourseListActivity,  LinearLayoutManager.VERTICAL, false)
                adapter = courseListAdapter.apply {
                    setOnItemClickListener(this@CourseListActivity)
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val totalItemCount = layoutManager.itemCount
                    }
                })
            }
        }
    }

    override fun <T> onClickedListener(item: T) {
        if (item is CourseEntity)  presenter.goToDetail(data = item)
    }

}