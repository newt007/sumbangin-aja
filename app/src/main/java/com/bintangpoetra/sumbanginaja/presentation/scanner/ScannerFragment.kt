package com.bintangpoetra.sumbanginaja.presentation.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentBarcodeScannerBinding
import com.bintangpoetra.sumbanginaja.utils.ext.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.android.ext.android.inject

class ScannerFragment: Fragment(), ZXingScannerView.ResultHandler {

    private var _binding: FragmentBarcodeScannerBinding? = null
    private val binding get() = _binding!!

    private var zXingScannerView: ZXingScannerView? = null

    private val scannerViewModel: ScannerViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askPermission()

        initUI()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        zXingScannerView?.startCamera()
    }

    private fun initUI() {
        binding.lottieLoading.initLottie()
        initScannerView()
    }

    private fun initScannerView() {
        zXingScannerView = ZXingScannerView(requireContext())
        zXingScannerView!!.setAutoFocus(true)
        zXingScannerView!!.setResultHandler(this)
        binding.frameCamera.addView(zXingScannerView)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> initScannerView()
            else -> {}
        }
    }

    private fun checkPermission(permission: Array<String>): Boolean = permission.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun askPermission() {
        val listOfPermission = arrayOf(
            Manifest.permission.CAMERA
        )
        if (!checkPermission(listOfPermission)) {
            ActivityCompat.requestPermissions(requireActivity(), listOfPermission, 1)
        }
    }

    override fun handleResult(result: Result?) {
        scannerViewModel.scanningFood(result?.text.toString(), "1", "5")
    }

    private fun initObservers() {
        scannerViewModel.scanningFoodResult.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.backgroundDim)
                    }
                }
                is ApiResponse.Success -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.backgroundDim)
                    }
                    showCustomDialog(
                        image = R.drawable.ic_success_illustration,
                        title = getString(R.string.label_transaction_is_completed),
                        description = getString(R.string.label_thank_you_food_ranger),
                        btnText = getString(R.string.action_back_to_home),
                        onBtnClick = {
                            findNavController().navigate(R.id.action_scannerFragment_to_homeFragment)
                        }
                    )
                }
                is ApiResponse.Error -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.backgroundDim)
                    }
                    showToast(response.errorMessage)
                }
                else -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.backgroundDim)
                    }
                }
            }
        }
    }

}