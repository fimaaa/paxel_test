package com.example.paxeltest.ui.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.paxeltest.R
import com.example.paxeltest.base.BaseFragment
import com.example.paxeltest.base.BaseLoadStateAdapter
import com.example.paxeltest.databinding.FragmentExampleBinding
import com.example.paxeltest.ui.adapter.example.ExamplePagingAdapter
import com.example.paxeltest.utill.*
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import java.net.HttpURLConnection

class ExampleFragment:
//    Fragment() {
    BaseFragment() {
//    val binding = FragmentExampleBinding.inflate(layoutInflater)
    private val viewModel: ExampleViewModel by  viewModels()
    private var _binding: FragmentExampleBinding? = null
    val binding get() = _binding!!
    private var skeleton: Skeleton? = null

    private val adapter = ExamplePagingAdapter {
        requireContext().showSnackBar(binding.root, it.login, Toast_Default)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onInitialization() {
        binding.apply {
            rcvExample.adapter = adapter.withLoadStateHeaderAndFooter(
                header = BaseLoadStateAdapter { adapter.retry() },
                footer = BaseLoadStateAdapter { adapter.retry() }
            )
            skeleton = rcvExample.applySkeleton(R.layout.item_example_recyclerview)
            skeleton?.showShimmer = true
        }

    }

    override fun onObserveAction() {
        observe(viewModel.exampleList) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadState ->
            if(skeleton?.isSkeleton() == true || adapter.itemCount < 1) {
                binding.apply {
                    blanklayout.gone()
                    rcvExample.visible()
                    when (loadState.source.refresh) {
                        is LoadState.Loading -> {
                            skeleton?.showSkeleton()
                        }
                        is LoadState.Error -> {
                            skeleton?.showOriginal()
                            val throwable = (loadState.source.refresh as LoadState.Error).error
                            rcvExample.gone()
                            blanklayout.visible()
                            blanklayout.setType(
                                ErrorUtils.getErrorThrowableCode(throwable),
                                ErrorUtils.getErrorThrowableMsg(throwable)
                            )
                            blanklayout.setOnClick(getString(R.string.retry)){
                                adapter.retry()
                            }
                        }
                        is LoadState.NotLoading -> {
                            skeleton?.showOriginal()
                            if (loadState.source.refresh is LoadState.NotLoading &&
                                loadState.append.endOfPaginationReached &&
                                adapter.itemCount < 1
                            ) {
                                rcvExample.gone()
                                blanklayout.visible()
                                blanklayout.setType(HttpURLConnection.HTTP_NO_CONTENT)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onReadyAction() {
    }
}