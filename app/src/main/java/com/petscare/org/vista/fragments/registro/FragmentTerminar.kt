package com.petscare.org.vista.fragments.registro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petscare.org.R
import com.petscare.org.databinding.FragmentNombreBinding
import com.petscare.org.databinding.FragmentTerminarBinding
import com.petscare.org.vista.activitys.ActivityMenu

class FragmentTerminar : Fragment() {

    private var _binding: FragmentTerminarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTerminarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTerminar.setOnClickListener {

            startActivity(Intent(requireContext(),ActivityMenu::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            requireActivity().finish()
        }
    }
}