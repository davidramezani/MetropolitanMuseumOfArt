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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
            override fun onItemClick(item: Int) {
                findNavController().navigate("metropolitanmuseumofart.com://detail?objectId=${item}".toUri())
            }

            override fun onListChange() {
                resetPosition()
            }
        })
        binding.rvSearchedItemsSearchFragment.adapter = searchListAdapter
    }

    private fun setSearchTextWatcher() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchQueryChanged(text.toString())
        }
    }

    private fun observeUiState() {
        collectLatestLifecycleFlow(viewModel.searchResultUiState) { searchResultUiState ->
            when (searchResultUiState) {
                SearchResultUiState.EmptyQuery -> {
                    binding.apply {
                        tvSearchTotalResult.visibility = View.VISIBLE
                        rvSearchedItemsSearchFragment.visibility = View.VISIBLE
                        cpLoadingItems.visibility = View.GONE
                        grErrorElements.visibility = View.GONE
                        tvSearchTotalResult.text = getString(
                            R.string.total_number_of_ids,
                            "0"
                        )
                    }
                    searchListAdapter.submitList(emptyList())
                }

                SearchResultUiState.Loading -> {
                    binding.apply {
                        cpLoadingItems.visibility = View.VISIBLE
                        tvSearchTotalResult.visibility = View.GONE
                        rvSearchedItemsSearchFragment.visibility = View.GONE
                        grErrorElements.visibility = View.GONE
                    }
                }

                is SearchResultUiState.LoadFailed -> {
                    binding.apply {
                        grErrorElements.visibility = View.VISIBLE
                        cpLoadingItems.visibility = View.GONE
                        tvSearchTotalResult.visibility = View.GONE
                        rvSearchedItemsSearchFragment.visibility = View.GONE
                        tvErrorMessage.text = getString(searchResultUiState.errorMessage)
                    }
                }

                is SearchResultUiState.Success -> {
                    binding.apply {
                        tvSearchTotalResult.visibility = View.VISIBLE
                        rvSearchedItemsSearchFragment.visibility = View.VISIBLE
                        cpLoadingItems.visibility = View.GONE
                        grErrorElements.visibility = View.GONE
                        tvSearchTotalResult.text = getString(
                            R.string.total_number_of_ids,
                            searchResultUiState.totalItems.toString()
                        )
                    }
                    searchListAdapter.submitList(searchResultUiState.items)
                }
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