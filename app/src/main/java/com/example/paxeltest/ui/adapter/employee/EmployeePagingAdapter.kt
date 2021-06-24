package com.example.paxeltest.ui.adapter.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paxeltest.data.model.Employee
import com.example.paxeltest.databinding.ItemEmployeeRecyclerviewBinding

class EmployeePagingAdapter(
    private val listener: (Employee.Data) -> Unit
) : PagingDataAdapter<Employee.Data, EmployeePagingAdapter.ViewHolder>(TASK_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemEmployeeRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemEmployeeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { listener.invoke(it) }
                }
            }
        }

        fun bind(exampleData: Employee.Data) {
            binding.data = exampleData
        }
    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Employee.Data>() {
            override fun areItemsTheSame(
                oldItem: Employee.Data,
                newItem: Employee.Data
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Employee.Data,
                newItem: Employee.Data
            ): Boolean = oldItem == newItem
        }
    }
}