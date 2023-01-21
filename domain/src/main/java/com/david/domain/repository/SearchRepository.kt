package com.david.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchObjectIDs(searchQuery : String) : Flow<List<Int>>
}