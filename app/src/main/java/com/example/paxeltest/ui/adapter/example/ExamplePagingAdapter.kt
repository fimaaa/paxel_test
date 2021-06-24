package com.example.paxeltest.ui.adapter.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paxeltest.data.model.Example
import com.example.paxeltest.databinding.ItemExampleRecyclerviewBinding

class ExamplePagingAdapter(
    private val listener : (Example.Data) -> Unit
) : PagingDataAdapter<Example.Data, ExamplePagingAdapter.ViewHolder>(TASK_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemExampleRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemExampleRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    getItem(position)?.let{ listener.invoke(it) }
                }
            }
        }

        fun bind(exampleData: Example.Data) {
           binding.data = exampleData
        }
    }

    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Example.Data>() {
            override fun areItemsTheSame(
                oldItem: Example.Data,
                newItem: Example.Data
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: Example.Data,
                newItem: Example.Data
            ): Boolean = oldItem == newItem
        }
    }
}