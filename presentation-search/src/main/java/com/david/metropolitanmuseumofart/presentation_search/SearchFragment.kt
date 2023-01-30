package com.david.metropolitanmuseumofart.presentation_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.david.metropolitanmuseumofart.presentation_common.extensions.collectLatestLifecycleFlow
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import com.david.metropolitanmuseumofart.presentation_search.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setSearchTextWatcher()
        observeUiState()
    }

    private fun setupRecyclerView() {
        searchListAdapter = SearchListAdapter(object : SearchListAdapter.OnListeners {
            override fun onItemClick(item: SearchedListItemModel) {
                findNavController().navigate("metropolitanmuseumofart.com://detail?objectId=${item.id}".toUri())
            }
            override fun onListChange() {
                resetPosition()
            }
        })
        binding.rvSearchedItemsSearchFragment.adapter = searchListAdapter
    }

    private fun setSearchTextWatcher() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchQueryChange(text.toString())
        }
    }

    private fun observeUiState() {
        collectLatestLifecycleFlow(viewModel.searchedListFlow) { uiState ->
            if (uiState is UiState.Success) {
                searchListAdapter.submitList(uiState.data.items)
            }
        }
    }

    private fun resetPosition() {
        binding.rvSearchedItemsSearchFragment.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}