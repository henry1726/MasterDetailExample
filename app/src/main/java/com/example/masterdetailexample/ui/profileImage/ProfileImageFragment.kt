package com.example.masterdetailexample.ui.profileImage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.masterdetailexample.R
import com.example.masterdetailexample.databinding.FragmentProfileImageBinding
import com.example.masterdetailexample.ui.location.MapActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProfileImageFragment : Fragment() {

    private val binding: FragmentProfileImageBinding by lazy {
        FragmentProfileImageBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    private fun initUI(){
        with(binding){
            btnGeneratePhoto.setOnClickListener {

                if (tietProfilePhoto.text.isNullOrEmpty().not() && tietPhrase.text.isNullOrEmpty()){
                    Glide
                        .with(requireContext())
                        .load(tietProfilePhoto.text.toString())
                        .error(R.drawable.baseline_account_circle_24)
                        .into(ivProfilePhoto)
                    ivProfilePhoto.visibility = View.VISIBLE
                }

                if(tietProfilePhoto.text.isNullOrEmpty() && tietPhrase.text.isNullOrEmpty().not()){
                    val stringArray = tietPhrase.text.toString().split(" ")
                    if (stringArray.size > 1){
                        val firstName = stringArray.first().substring(0, 1).toUpperCase(Locale.ROOT)
                        val secondName = stringArray[1].substring(0, 1).toUpperCase(Locale.ROOT)
                        tvCapitalLetters.text = "$firstName$secondName"
                    }else{
                        val firstName = stringArray.first().substring(0, 1).toUpperCase(Locale.ROOT)
                        tvCapitalLetters.text = "$firstName"
                    }
                    tvCapitalLetters.visibility = View.VISIBLE
                }

                if (tietProfilePhoto.text.isNullOrEmpty() && tietPhrase.text.isNullOrEmpty()){
                    tvCapitalLetters.visibility = View.GONE
                    Glide
                        .with(requireContext())
                        .load(R.drawable.baseline_account_circle_24)
                        .into(ivProfilePhoto)
                    ivProfilePhoto.visibility = View.VISIBLE
                }

            }
            btnGoToView4.setOnClickListener {
                startActivity(Intent(requireContext(), MapActivity::class.java))
            }
        }
    }
}