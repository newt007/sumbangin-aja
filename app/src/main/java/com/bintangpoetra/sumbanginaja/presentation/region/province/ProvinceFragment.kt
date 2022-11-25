package com.bintangpoetra.sumbanginaja.presentation.region.province

import android.os.Bundle
import android.view.LayoutInflater
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
import com.bintangpoetra.sumbanginaja.databinding.FragmentProvinceBinding
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.presentation.region.adapter.RegionAdapter
import com.bintangpoetra.sumbanginaja.presentation.region.city.CityFragment
import com.bintangpoetra.sumbanginaja.utils.ext.hideLoading
import com.bintangpoetra.sumbanginaja.utils.ext.showLoading
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProvinceFragment : BaseFragment<FragmentProvinceBinding>() {

    private val viewModel: ProvinceViewModel by inject()
    private val adapter: RegionAdapter by lazy { RegionAdapter { onRegionItemClick(it) } }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProvinceBinding = FragmentProvinceBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        initAdapter()
        binding.toolbarAccount.apply {
            title = context.getString(R.string.title_choose_province)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun initAction() {
    }

    override fun initProcess() {
        lifecycleScope.launch {
            viewModel.provincesResult.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun initObservers() {
    }

    private fun initAdapter() {
        binding.rvProvince.adapter = adapter
        binding.rvProvince.layoutManager =
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
        binding.rvProvince.scheduleLayoutAnimation()
    }

    private fun onRegionItemClick(region: Region) {
        setFragmentResult(
            PROVINCE_ID_KEY,
            bundleOf(PROVINCE_ID_BUNDLE to region.id)
        )
        setFragmentResult(
            PROVINCE_NAME_KEY,
            bundleOf(PROVINCE_NAME_BUNDLE to region.name)
        )
        findNavController().navigateUp()
    }

    companion object {
        const val PROVINCE_ID_KEY = "ProvinceIDKey"
        const val PROVINCE_NAME_KEY = "ProvinceNameKey"
        const val PROVINCE_ID_BUNDLE = "ProvinceIDBundle"
        const val PROVINCE_NAME_BUNDLE = "ProvinceIDBundle"
    }

}