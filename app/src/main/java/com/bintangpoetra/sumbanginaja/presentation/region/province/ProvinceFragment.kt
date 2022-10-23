package com.bintangpoetra.sumbanginaja.presentation.region.province

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.databinding.FragmentProvinceBinding
import com.bintangpoetra.sumbanginaja.presentation.region.adapter.RegionAdapter
import com.bintangpoetra.sumbanginaja.utils.ext.hideLoading
import com.bintangpoetra.sumbanginaja.utils.ext.showLoading
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProvinceFragment : Fragment() {

    private var _binding: FragmentProvinceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProvinceViewModel by inject()
    private lateinit var adapter: RegionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProvinceBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAdapter()
        initObservers()
    }

    private fun initUI(){
        binding.toolbarAccount.apply {
            title = context.getString(R.string.title_choose_province)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.provincesResult.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initAdapter() {
        adapter = RegionAdapter {
            setFragmentResult(PROVINCE_ID_KEY, bundleOf(PROVINCE_ID_BUNDLE to it.id))
            setFragmentResult(PROVINCE_NAME_KEY, bundleOf(PROVINCE_NAME_BUNDLE to it.name))
            findNavController().navigateUp()
        }
        binding.rvProvince.adapter = this.adapter
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

    companion object {
        const val PROVINCE_ID_KEY = "ProvinceIDKey"
        const val PROVINCE_NAME_KEY = "ProvinceNameKey"
        const val PROVINCE_ID_BUNDLE = "ProvinceIDBundle"
        const val PROVINCE_NAME_BUNDLE = "ProvinceIDBundle"
    }

}