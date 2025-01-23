package com.reza.feature.home.presentation

/**
 * Represents the events that can occur on the Home screen.
 */
sealed interface HomeEvent {

    /**
     * Represents an event to fetch or refresh the list of continents.
     */
    data class GetContinents(val isRefreshing: Boolean = false) : HomeEvent

    /**
     * Represents an event to consume and clear an error message.
     */
    data object ConsumeErrorMessage : HomeEvent
}