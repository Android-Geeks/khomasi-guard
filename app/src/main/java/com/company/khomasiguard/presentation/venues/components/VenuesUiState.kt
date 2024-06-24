package com.company.khomasiguard.presentation.venues.components

import com.company.khomasiguard.domain.model.playground.Playground

data class VenuesUiState (
    val playgroundName : String=" ",
    var playgroundId : Int=0,
    val activated: List<Playground> = listOf(),
    val notActivated: List<Playground> = listOf(),
    val isLoading: Boolean = false,
    var isBookable: Boolean = true,
    val errorMessage: String? = null
)