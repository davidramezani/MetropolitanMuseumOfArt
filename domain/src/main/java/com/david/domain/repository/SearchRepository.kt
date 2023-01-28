package com.david.domain.repository

import com.david.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchObjectIDs(searchQuery : String) : Flow<SearchResult>
}