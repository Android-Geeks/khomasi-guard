package com.company.khomasiguard.presentation.venues.components

import com.company.khomasiguard.domain.model.playground.Playground
import com.company.khomasiguard.domain.model.playground.PlaygroundX

data class VenuesUiState (
    val activated: List<Playground> = listOf(),
    val notActivated: List<Playground> = listOf(),
)
