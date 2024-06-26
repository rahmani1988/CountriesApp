package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

interface ContinentsUseCase {
    suspend fun getContinents(): ResultState<List<Continent>>
}