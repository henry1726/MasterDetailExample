package com.example.masterdetailexample.ui.profileImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.masterdetailexample.databinding.FragmentProfileImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileImageFragment : Fragment() {

    private val binding: FragmentProfileImageBinding by lazy {
        FragmentProfileImageBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Glide
            .with(myFragment)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.loading_spinner)
            .into(myImageView);*/

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }
}