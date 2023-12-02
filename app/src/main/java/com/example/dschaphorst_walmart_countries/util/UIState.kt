package com.example.dschaphorst_walmart_countries.util

import com.example.dschaphorst_walmart_countries.model.Countries

sealed class UIState {
    object LOADING : UIState()
    data class SUCCESS(val countries: Countries) : UIState()
    data class ERROR(val error: Exception) : UIState()
}