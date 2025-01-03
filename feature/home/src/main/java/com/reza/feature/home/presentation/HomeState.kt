package com.reza.feature.home.presentation

import androidx.annotation.DrawableRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.reza.feature.home.domain.model.Continent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable

/**
 * Represents the UI state of the Home screen.
 *
 * This sealed interface defines the different states that the Home screen can be in:
 * - **Loading:** Indicates that data is being loaded.
 * - **Success:** Indicates that data has been loaded successfully and displays a list of [ContinentView]s. This state is immutable.
 * - **Error:** Indicates that an error occurred while loading data and displays an error message.
 * - **Empty:** Indicates that there is no data to display.
 */
sealed interface HomeUiState {

    /**
     * Represents the loading state.
     */
    data object Loading : HomeUiState

    /**
     * Represents the success state. This state is immutable, meaning its properties cannot be changed after it is created.
     *
     * @property continents The list of [ContinentView]s to display.
     */
    @Immutable
    data class Success(val continents: List<ContinentView>) : HomeUiState

    /**
     * Represents the error state.
     *
     * @property errorMessage The error message to display.
     */
    data class Error(val errorMessage: String) : HomeUiState

    /**
     * Represents the empty state.
     */
    data object Empty : HomeUiState
}

/**
 * Represents a view of a continent.
 *
 * This data class holds information about a continent, including its name and an associated image resource.
 *
 * @property continent The [Continent] object representing the continent.
 * @property imageResource The image resource ID associated with the continent.
 */
data class ContinentView(
    val continent: Continent,
    @DrawableRes val imageResource: Int
)

@Stable
class HomeStateHolder(
    val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    fun showSnackBar(
        message: String,
        actionLabel: String,
        resultCallback: (SnackbarResult) -> Unit
    ) {
        scope.launch {
            val result =
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
            resultCallback(result)
        }
    }
}

@Composable
fun rememberHomeScreenState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    HomeStateHolder(scope = scope, snackBarHostState = snackBarHostState)
}