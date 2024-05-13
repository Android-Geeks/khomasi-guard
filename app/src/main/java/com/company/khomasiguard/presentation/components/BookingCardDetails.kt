package com.company.khomasiguard.presentation.components

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun BookingCardDetails(
    modifier: Modifier = Modifier,
    bookingDetails: Booking,
    onClickCall: () -> Unit,
    playgroundName: String,
    context: Context = LocalContext.current,
    onClickCancelBooking: () -> Unit,
    toRate: () -> Unit

) {
    val bookingStartTime = remember {
        bookingDetails.bookingTime.toDateTime()?.toFormattedTimeString()
    }
    val bookingEndTime = remember {
        bookingDetails.bookingTime.toDateTime()?.toAddTime(bookingDetails.duration)
            ?.toFormattedTimeString()
    }
    val bookingDate =
        remember { bookingDetails.bookingTime.toDateTime()?.toFormattedDateString() }

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)
        ) {
            UserDefinition(
                userName = bookingDetails.userName,
                profileImg = bookingDetails.profilePicture,
                userRate = bookingDetails.rating.toString(),
                onClickCall = onClickCall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            HeaderText(
                mainText = stringResource(R.string.playground),
                subText = playgroundName
            )
            HeaderText(
                mainText = stringResource(R.string.booking_price),
                subText = context.getString(R.string.fees_for_booking, bookingDetails.cost)
            )
            HeaderText(
                mainText = stringResource(id = R.string.booking_date),
                subText = bookingDate ?: "10-10-2010"
            )
            HeaderText(
                mainText = stringResource(id = R.string.booking_time),
                subText = "$bookingStartTime ${stringResource(id = R.string.to)} ${bookingEndTime ?: "null"}"
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.outline
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithIcon(
                    text = stringResource(id = R.string.otp_code),
                    iconId = R.drawable.key
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = bookingDetails.confirmationCode,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start,
                )
            }
        }
        Column {
            DashedDivider()
            MyOutlinedButton(
                text = R.string.cancel_booking,
                icon = R.drawable.xcircle,
                onClick = onClickCancelBooking,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            MyButton(text =R.string.rate, onClick = { toRate() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 16.dp, end = 16.dp, bottom = 20.dp))
        }

    }

}

@Composable
fun DashedDivider(
    color: Color = MaterialTheme.colorScheme.outline,
    strokeWidth: Float = 2.dp.value,
    dashLength: Float = 100f,
    dashGap: Float = 50f
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, center.y),
            end = Offset(size.width, center.y),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, dashGap))
        )
    }
}

@Composable
fun HeaderText(
    mainText: String,
    subText: String
) {
    Column {
        Text(
            text = "$mainText:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Start,
        )
        Text(
            text = subText,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview(locale = "en")
@Composable
fun BookingCard2Preview() {
    KhomasiGuardTheme {
        BookingCardDetails(
            bookingDetails = Booking(
                bookingTime = "2024-05-05T04:15:00",
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
            onClickCall = {},
            playgroundName = "playgroundName",
            onClickCancelBooking = {},
            toRate = {}
        )
    }


}