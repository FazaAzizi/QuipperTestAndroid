package com.faza.quippertest.module.course_list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faza.quippertest.common.BaseViewHolder
import com.faza.quippertest.databinding.ItemCourseListBinding
import com.faza.quippertest.module.course_list.entity.CourseEntity
import com.faza.quippertest.utils.Utils.setImageViewUrl
import com.faza.quippertest.utils.Utils.setOnSingleClickListener

class RecyclerViewCourseListAdapter() : RecyclerView.Adapter<BaseViewHolder>() {
    private val courseList: MutableList<CourseEntity> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CourseListHolder(
            ItemCourseListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCourseList(list: List<CourseEntity>, clearAll: Boolean = false) {
        if (clearAll)
            courseList.clear()
        courseList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is CourseListHolder -> holder.bind(courseList[position])
        }
    }

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun <T> onClickedListener(item: T)
    }

    fun setOnItemClickListener(onClickListener: OnItemClickListener) {
        this.onItemClickListener = onClickListener
    }

    inner class CourseListHolder(
        private val binding: ItemCourseListBinding
    ) : BaseViewHolder(binding) {
        val context: Context = binding.root.context
        fun bind(data: CourseEntity) = with(binding) {
            ivCourseList.setImageViewUrl(data.thumbnailURL)
            tvTitleCourse.text = data.title
            tvDate.text = data.presenterName

            root.setOnSingleClickListener {
                this@RecyclerViewCourseListAdapter.onItemClickListener?.onClickedListener(data)
            }
        }
    }
}