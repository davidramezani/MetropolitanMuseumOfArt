package com.david.metropolitanmuseumofart.presentation_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.david.metropolitanmuseumofart.presentation_common.extensions.collectLatestLifecycleFlow
import com.david.metropolitanmuseumofart.presentation_common.state.UiSingleEvent
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import com.david.metropolitanmuseumofart.presentation_detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        observeUiSingleEvent()
    }

    private fun observeUiState() {
        collectLatestLifecycleFlow(viewModel.museumObjectFlow) { uiState ->
            if (uiState is UiState.Success) {
                updateUI(uiState.data)
            }
        }
    }

    private fun updateUI(data: DetailModel) {
        binding.apply {
            tvObjectTitle.text = data.title
            tvObjectName.text = data.objectName
            tvDepartment.text = data.department
            tvArtist.text = data.artist
            tvArtistBio.text = data.artistBio
            tvMedium.text = data.medium
            setupImageSlider(data.images)
        }
    }

    private fun setupImageSlider(imageList: List<String>) {
        imageSliderAdapter = ImageSliderAdapter(imageList)
        binding.apply {
            vpImageSlider.adapter = imageSliderAdapter
        }
        binding.indicator.setViewPager(binding.vpImageSlider)
    }

    private fun observeUiSingleEvent() {
        collectLatestLifecycleFlow(viewModel.singleEventFlow) {
            if(it is DetailSingleEvent.CloseDetailFragment) {
                findNavController().navigateUp()
            }
        }
    }

}