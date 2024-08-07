package com.vahossmedia.android.mylocalrestaurantfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahossmedia.android.mylocalrestaurantfinder.YelpRepository
import com.vahossmedia.android.mylocalrestaurantfinder.YelpService
import com.vahossmedia.android.mylocalrestaurantfinder.model.Business
import com.vahossmedia.android.mylocalrestaurantfinder.model.Category
import com.vahossmedia.android.mylocalrestaurantfinder.model.Coordinates
import com.vahossmedia.android.mylocalrestaurantfinder.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BusinessListViewModel : ViewModel() {
    private val yelpRepository = YelpRepository(YelpService.getYelpService())

    private val _businessList = MutableStateFlow<List<Business>>(emptyList())
    val businessList = _businessList.asStateFlow()

    private val _uiState = MutableStateFlow<RestaurantUiState>(RestaurantUiState.Loading)
    val uiState: StateFlow<RestaurantUiState> = _uiState.asStateFlow()

    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)
    val location: StateFlow<Pair<Double, Double>?> = _location.asStateFlow()

    fun fetchRestaurants(pair: Pair<Double, Double>? = null) {
        viewModelScope.launch {
            _uiState.value = RestaurantUiState.Loading
            try {
                val response = if (pair == null) yelpRepository.fetchBusinesses(location = "Vancouver")
                else yelpRepository.fetchBusinesses(pair.first, pair.second)
                _uiState.value = RestaurantUiState.Success(response.businesses)
            } catch (e: Exception) {
                _uiState.value = RestaurantUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    // TODO move to testing
    private fun fetchMockRestaurants() {
        val mockBusinessList = mutableListOf<Business>()
        for (i in 1 until 50) {
            val business = Business(
                id = i.toString(),
                alias = "lonsdale-quay-market",
                name = "Lonsdale Quay Market $i",
                imageUrl = "https://example.com/lonsdale-quay.jpg",
                isClosed = false,
                url = "https://lonsdalequay.com",
                reviewCount = 800,
                categories = listOf(Category("market"), Category("shopping")),
                rating = 4.2,
                coordinates = Coordinates(49.3098, -123.0827),
                transactions = null,
                price = "$$",
                location = Location(
                    "123 Carrie Cates Court",
                    city = "North Vancouver",
                    state = "BC",
                    zipCode = "V7M 3K7",
                    country = "Canada"
                ),
                phone = "+16049864483",
                displayPhone = "(604) 986-4483",
                distance = 0.5,
                description = "Waterfront market with local vendors, restaurants, and shops."
            )
            mockBusinessList.add(business)
        }

        viewModelScope.launch {
            updateBusinesses(mockBusinessList)
        }
    }

    private suspend fun updateBusinesses(newItems: List<Business>) {
        _businessList.emit(newItems)
    }

    fun setLocation(latitude: Double, longitude: Double) {
        _location.value = Pair(latitude, longitude)
        viewModelScope.launch {
            fetchRestaurants(_location.value)
        }
    }
}

sealed class RestaurantUiState {
    data object Loading : RestaurantUiState()
    data class Success(val restaurants: List<Business>) : RestaurantUiState()
    data class Error(val message: String) : RestaurantUiState()
}
