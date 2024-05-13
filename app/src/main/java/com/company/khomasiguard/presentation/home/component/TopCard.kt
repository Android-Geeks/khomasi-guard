package com.company.khomasiguard.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.model.booking.GuardBooking
import com.company.khomasiguard.presentation.home.HomeUiState
import com.company.khomasiguard.theme.Cairo
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TopCard(
    booking: GuardBooking,
    uiState: StateFlow<HomeUiState>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(247.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(
                    MaterialTheme.colorScheme.onBackground
                )
                .padding(start = 16.dp, end = 16.dp)
        )
        {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 16.sp
                            )
                        )
                        {
                            append(stringResource(id = R.string.hello_captain) + "\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 14.sp
                            )
                        ) {
                            append(uiState.value.date)
                        }

                    },
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.shapes.small
                        )

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.signout),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(75.dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.shapes.medium
                        )
                        .padding(bottom = 8.dp, start = 8.dp, top = 5.dp)
                        .weight(2f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = Modifier
                                .height(44.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.reservations_today),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = Cairo,
                                        fontWeight = FontWeight(500),
                                        fontSize = 18.sp
                                    )
                                )
                                {
                                    append(booking.bookingsCount.toString())
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = Cairo,
                                        fontWeight = FontWeight(500),
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append("\n"+ stringResource(id = R.string.bookings_today))
                                }
                            },
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.weight(0.5f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(32.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface,
                            MaterialTheme.shapes.small
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.contact_us),
                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

        }


    }
    Text(
        text = stringResource(id = R.string.bookings_today),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        color = MaterialTheme.colorScheme.onSurface

    )
}
