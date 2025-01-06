package com.reza.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    private val continentsImageUseCase: ContinentImageUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeUiState = _homeUiState.asStateFlow()

    private val _homeLoadingState = MutableStateFlow<HomeLoadingState>(HomeLoadingState.Idle)
    val homeLoadingState = _homeLoadingState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _homeUiState.update {
            HomeUiState.Error(exception.message ?: "Something went wrong, please try again!")
        }
    }

    private fun getContinents(isRefreshing: Boolean = false) {
        viewModelScope.launch(exceptionHandler) {
            // Loading state
            if (isRefreshing) {
                _homeLoadingState.update {
                    HomeLoadingState.Refreshing
                }
            } else {
                _homeLoadingState.update {
                    HomeLoadingState.Loading
                }
            }

            // Getting continents
            when (val result = continentsUseCase.getContinents()) {
                is ResultState.Success -> {
                    _homeUiState.update {
                        HomeUiState.Success(result.data.transformToContinentViews {
                            continentsImageUseCase.findContinentImage(it)
                        })
                    }
                }

                is ResultState.Failure -> {
                    _homeUiState.update {
                        HomeUiState.Error(result.error)
                    }
                }
            }
        }
    }

    fun consumeErrorMessage() {
//        _homeState.update { state ->
//            state.copy(errorMessage = null)
//        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetContinents -> getContinents(event.isRefreshing)
        }
    }
}

/**
 * Transforms a list of [Continent] objects into a list of [ContinentView] objects.
 *
 * This extension function iterates through the list of continents and creates a corresponding
 * [ContinentView] for each continent. It uses the provided `imageResource` function to
 * retrieve the image resource ID for each continent based on its name.
 *
 * @param imageResource A function that takes a continent name as input and returns the
 * corresponding image resource ID.
 * @return A list of [ContinentView] objects.
 */
fun List<Continent>.transformToContinentViews(imageResource: (String) -> Int): List<ContinentView> =
    this.map { continent ->
        ContinentView(
            continent = continent,
            imageResource = imageResource(continent.name)
        )
    }