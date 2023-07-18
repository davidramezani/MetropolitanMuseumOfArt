package com.david.metropolitanmuseumofart.presentation_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.david.domain.entity.MuseumObject
import com.david.metropolitanmuseumofart.presentation_common.extensions.collectLatestLifecycleFlow
import com.david.metropolitanmuseumofart.presentation_detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), View.OnClickListener {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeUiState()
        observeUiSingleEvent()
    }

    private fun setupUI() {
        binding.imvBackIcon.setOnClickListener(this)
    }

    private fun observeUiState() {
        collectLatestLifecycleFlow(viewModel.museumObjectUiState) { museumObjectUiState ->
            when (museumObjectUiState) {
                is MuseumObjectUiState.LoadFailed -> {
                    binding.apply {
                        tvErrorMessage.visibility = View.VISIBLE
                        cpLoadingDetail.visibility = View.GONE
                        svMuseumObjectDetail.visibility = View.GONE
                        vpImageSlider.visibility = View.GONE
                        indicator.visibility = View.GONE
                        imvTitleBackgroundShadow.visibility = View.GONE
                        tvObjectTitle.visibility = View.GONE
                    }
                }

                MuseumObjectUiState.Loading -> {
                    binding.apply {
                        cpLoadingDetail.visibility = View.VISIBLE
                        svMuseumObjectDetail.visibility = View.GONE
                        vpImageSlider.visibility = View.GONE
                        indicator.visibility = View.GONE
                        imvTitleBackgroundShadow.visibility = View.GONE
                        tvObjectTitle.visibility = View.GONE
                        tvErrorMessage.visibility = View.GONE
                    }
                }

                is MuseumObjectUiState.Success -> {
                    binding.apply {
                        svMuseumObjectDetail.visibility = View.VISIBLE
                        vpImageSlider.visibility = View.VISIBLE
                        indicator.visibility = View.VISIBLE
                        imvTitleBackgroundShadow.visibility = View.VISIBLE
                        tvObjectTitle.visibility = View.VISIBLE
                        cpLoadingDetail.visibility = View.GONE
                        updateUI(museumObjectUiState.museumObject)
                    }
                }
            }
        }
    }

    private fun updateUI(data: MuseumObject) {
        binding.apply {
            tvObjectTitle.text = data.title
            tvObjectName.text = data.objectName
            tvDepartment.text = data.department
            tvArtist.text = data.artist
            tvArtistBio.text = data.artistBio
            tvMedium.text = data.medium
            setupImageSlider(data.additionalImages)
        }
    }

    private fun setupImageSlider(imageList: List<String>) {
        imageSliderAdapter = ImageSliderAdapter(imageList)
        binding.apply {
            vpImageSlider.adapter = imageSliderAdapter
        }
        binding.indicator.setViewPager(binding.vpImageSlider)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imv_back_icon -> {
                viewModel.backIconClicked()
            }
        }
    }

    private fun observeUiSingleEvent() {
        collectLatestLifecycleFlow(viewModel.singleEventFlow) {
            if (it is DetailSingleEvent.CloseDetailFragment) {
                findNavController().navigateUp()
            }
        }
    }

}