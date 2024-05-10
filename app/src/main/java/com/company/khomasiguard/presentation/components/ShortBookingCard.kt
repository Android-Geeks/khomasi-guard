package com.company.khomasiguard.presentation.components


import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.toAddTime
import com.company.khomasiguard.util.toDateTime
import com.company.khomasiguard.util.toFormattedDateString
import com.company.khomasiguard.util.toFormattedTimeString


@Composable
fun ShortBookingCard(
    modifier: Modifier = Modifier,
    bookingDetails: Booking,
    playgroundName: String,
    context: Context = LocalContext.current,
    onClickViewBooking: () -> Unit,
    onClickCall: () -> Unit,
) {
    val bookingStartTime =
        remember { bookingDetails.bookingTime.toDateTime()?.toFormattedTimeString() }
    val bookingEndTime = remember {
        bookingDetails.bookingTime.toDateTime()?.toAddTime(bookingDetails.duration)
            ?.toFormattedTimeString()
    }
    val bookingDate =
        remember { bookingDetails.bookingTime.toDateTime()?.toFormattedDateString() }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            UserDefinition(
                userName = bookingDetails.userName,
                profileImg = bookingDetails.profilePicture,
                userRate = bookingDetails.rating.toString(),
                onClickCall = onClickCall,
                modifier = Modifier.padding(8.dp)
            )

            BookingCardContent(
                playgroundName = playgroundName,
                bookingDate = bookingDate ?: "",
                bookingTime = "$bookingStartTime ${stringResource(id = R.string.to)} $bookingEndTime",
                bookingPrice = bookingDetails.cost,
                context = context,
                onClickViewBooking = onClickViewBooking
            )
        }

    }
}


@Composable
fun BookingCardContent(
    playgroundName: String,
    bookingDate: String,
    bookingTime: String,
    bookingPrice: Int,
    context: Context,
    onClickViewBooking: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextWithIcon(
                text = playgroundName,
                iconId = R.drawable.soccerball,
            )
            Spacer(modifier = Modifier.weight(1f))
                TextWithIcon(
                    text = context.getString(R.string.fees_for_booking, bookingPrice),
                    iconId = R.drawable.currencycircledollar,
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .height(34.dp)
                        .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                        .background(MaterialTheme.colorScheme.background)
                )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(
            text = bookingDate,
            iconId = R.drawable.calendar,
        )
        TextWithIcon(text = bookingTime, iconId = R.drawable.clock)

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 16.dp, bottom = 13.dp),
            color = MaterialTheme.colorScheme.outline
        )
    }
    MyButton(
        text = R.string.view_booking,
        onClick = { onClickViewBooking() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        icon = R.drawable.arrowupleft
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    color: Color = MaterialTheme.colorScheme.tertiary,
    textDecoration: TextDecoration = TextDecoration.None,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = textStyle,
            color = color,
            textAlign = TextAlign.Start,
            textDecoration = textDecoration
        )
    }
}

@Preview
@Composable
private fun BookingCardPreview() {
    KhomasiGuardTheme {
        ShortBookingCard(
            bookingDetails = Booking(
                bookingTime = "2024-05-05T00:15:00",
                userName = "userName",
                profilePicture = "profilePicture",
                rating = 4,
                cost = 50,
                email = "email",
                phoneNumber = "phoneNumber",
                bookingNumber = 1,
                confirmationCode = "2345",
                isCanceled = false,
                duration = 47
            ),
            playgroundName = "Playground Name",
            onClickViewBooking = {},
            onClickCall = {}
        )
    }
}
