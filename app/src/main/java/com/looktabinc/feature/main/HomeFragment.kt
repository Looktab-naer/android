package com.looktabinc.feature.main

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.looktabinc.DesignUtil
import com.looktabinc.R
import com.looktabinc.base.BaseFragment
import com.looktabinc.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    var categoryListSize = 0
    private val neighborhoodAdapter by lazy {
        NeighborhoodAdapter().apply {
            setOnItemClickListener(object : NeighborhoodAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
                    (activity as MainActivity).changeOutAddNeighborhoodFragment()
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun initViews() {
        binding.viewModel = activityViewModel
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvNeighborhood.apply {
            val deco = SpaceDecoration(this.context, true)
            adapter = neighborhoodAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(deco)
            activityViewModel.getNeighborhood()

        }
    }


    override fun initObserves() {
        activityViewModel.neighborhoodList.observe(this) {
            categoryListSize = it.size
            neighborhoodAdapter.submitList(it)
        }
    }

    inner class SpaceDecoration(val context: Context, val horizontal: Boolean) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            when (parent.getChildAdapterPosition(view)) {
                0 -> {
                    outRect.left = DesignUtil.dpToPx(context, 20)
                }
                categoryListSize - 1 -> {
                    outRect.right = DesignUtil.dpToPx(context, 20)
                }
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }


}