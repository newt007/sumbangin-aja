package com.bintangpoetra.sumbanginaja.presentation.food.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentFoodListBinding
import com.bintangpoetra.sumbanginaja.presentation.home.adapter.FoodAdapter
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class FoodListFragment : BaseFragment<FragmentFoodListBinding>() {

    private val viewModel: FoodListViewModel by inject()

    private val adapter: FoodAdapter by lazy { FoodAdapter { toDetail(it) } }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFoodListBinding = FragmentFoodListBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.rvHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = this.adapter

        binding.toolbarFood.apply {
            title = context.getString(R.string.title_my_food_list)
        }
    }

    override fun initAction() {
        binding.apply {
            toolbarFood.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnAddFood.click {
                findNavController().navigate(FoodListFragmentDirections.actionFoodListFragmentToAddFoodFragment())
            }
            btnToAddFood.click {
                findNavController().navigate(FoodListFragmentDirections.actionFoodListFragmentToAddFoodFragment())
            }
        }
    }

    override fun initProcess() {
        viewModel.getMyFoodsList()
    }

    override fun initObservers() {
        viewModel.foodResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        it.llEmptyState.gone()
                        showShimmerLoading(it.foodListShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                is ApiResponse.Success -> {
                    adapter.clear()
                    adapter.setData(response.data)
                    binding.let {
                        it.llEmptyState.gone()
                        it.rvHome.show()
                        hideShimmerLoading(it.foodListShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                is ApiResponse.Error -> {
                    binding.let {
                        it.llEmptyState.gone()
                        hideShimmerLoading(it.foodListShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                is ApiResponse.Empty -> {
                    binding.let {
                        it.btnAddFood.gone()
                        it.llEmptyState.show()
                        hideShimmerLoading(it.foodListShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                else -> {
                    binding.let {
                        it.llEmptyState.gone()
                        hideShimmerLoading(it.foodListShimmeringLoading, it.llShimmeringContainer)
                    }
                }
            }
        }
    }

    private fun toDetail(foodId: Int) {
        findNavController().navigate(
            FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(
                foodId
            )
        )
    }

}