package com.bintangpoetra.sumbanginaja.presentation.region.city

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentCityBinding
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.presentation.food.FoodDetailFragmentArgs
import com.bintangpoetra.sumbanginaja.presentation.region.adapter.RegionAdapter
import com.bintangpoetra.sumbanginaja.utils.ext.hideLoading
import com.bintangpoetra.sumbanginaja.utils.ext.showLoading
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class CityFragment : BaseFragment<FragmentCityBinding>() {

    private val viewModel: CityViewModel by inject()
    private val adapter: RegionAdapter by lazy { RegionAdapter { onRegionItemClick(it) } }

    private var provinceId = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCityBinding = FragmentCityBinding.inflate(inflater, container, false)

    override fun initIntent() {
        val safeArgs = arguments?.let { CityFragmentArgs.fromBundle(it) }
        provinceId = safeArgs?.idProvince ?: 0
    }

    override fun initUI(){
        initRv()
        binding.toolbarAccount.apply {
            title = context.getString(R.string.title_choose_city)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun initAction() {
    }

    override fun initProcess() {
        lifecycleScope.launch {
            viewModel.citiesResult(provinceId).collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }


    private fun initRv() {
        binding.rvCities.adapter = this.adapter
        binding.rvCities.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                if (adapter.itemCount < 1) {
                    Timber.d("Empty!")
                } else {
                    Timber.d("Ada isinya!")
                }
            }
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    showLoading(binding.loadingContainer)
                }
                is LoadState.NotLoading -> {
                    hideLoading(binding.loadingContainer)
                }
                else -> hideLoading(binding.loadingContainer)

            }
        }
        binding.rvCities.scheduleLayoutAnimation()
    }

    private fun onRegionItemClick(region: Region) {
        setFragmentResult(CITY_ID_KEY, bundleOf(CITY_ID_BUNDLE to region.id))
        setFragmentResult(CITY_NAME_KEY, bundleOf(CITY_NAME_BUNDLE to region.name))
        findNavController().navigateUp()
    }

    companion object {
        const val CITY_ID_KEY = "CityIDKey"
        const val CITY_NAME_KEY = "CityNameKey"
        const val CITY_ID_BUNDLE = "CityIDBundle"
        const val CITY_NAME_BUNDLE = "CityIDBundle"
    }

    override fun initObservers() {}

}