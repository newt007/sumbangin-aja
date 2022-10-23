package com.bintangpoetra.sumbanginaja.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.databinding.FragmentProfileBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.click
import com.bintangpoetra.sumbanginaja.utils.ext.getPrefManager
import com.bintangpoetra.sumbanginaja.utils.ext.showBarcodeDialog
import com.bintangpoetra.sumbanginaja.utils.ext.showConfirmDialog

class ProfileFragment : Fragment() {

    private var _fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = _fragmentProfileBinding!!

    private val pref: PreferenceManager by lazy { getPrefManager() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return _fragmentProfileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.apply {
            tvName.text = pref.getName
            tvEmail.text = pref.getEmail
        }
    }

    private fun initAction() {
        binding.apply {
            btnAccount.click {
                findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
            }
            btnLogout.click {
                showConfirmDialog(
                    onPositiveClick = {
                        logout()
                    }
                )
            }
            binding.btnQrCode.click {
                showBarcodeDialog(pref.getUserId.toString())
            }
            btnFoodList.click {
                findNavController().navigate(R.id.action_profileFragment_to_foodListFragment)
            }
        }
    }

    private fun logout() {
        pref.apply {
            clearAllPreferences()
        }
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

}