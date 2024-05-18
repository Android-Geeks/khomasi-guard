package com.company.khomasiguard.presentation.venues.components

import com.company.khomasiguard.domain.model.playground.Playground
import com.company.khomasiguard.domain.model.playground.PlaygroundInfo
import com.company.khomasiguard.domain.model.playground.PlaygroundX

data class VenuesUiState (
    val playgroundName : String=" ",
    val activated: List<Playground> = listOf(),
    val notActivated: List<Playground> = listOf(),
//    val playgroundX: Playground = Playground(
//        playgroundInfo = PlaygroundInfo(
//            playground = PlaygroundX(
//                id = 1,
//                name = "ZSC Playground",
//                feesForHour = 50,
//                address = "Nile Street, Zsc District, Cairo.",
//                isBookable = false
//            ),
//            picture = ""
//        ),
//        newBookings = 10,
//        finishedBookings = 23,
//        totalBookings = 33
//
//    ),
//    val notActivate: List<Playground> = listOf(playgroundX),
    )