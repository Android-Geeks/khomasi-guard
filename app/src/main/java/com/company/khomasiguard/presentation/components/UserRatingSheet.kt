package com.company.khomasiguard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.convertToBitmap
import com.company.khomasiguard.util.toAddTime
import com.company.khomasiguard.util.toDateTime
import com.company.khomasiguard.util.toFormattedTimeString
import com.gowtham.ratingbar.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRatingSheet(
    bookingDetails: Booking,
    playgroundName: String,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onClickButtonRate: () -> Unit,
) {

    val bookingStartTime = remember {
        bookingDetails.bookingTime.toDateTime()?.toFormattedTimeString()
    }
    val bookingEndTime = remember {
        bookingDetails.bookingTime.toDateTime()?.toAddTime(bookingDetails.duration)
            ?.toFormattedTimeString()
    }
    var userRate by remember { mutableFloatStateOf(0f) }
    MyModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(bookingDetails.profilePicture?.convertToBitmap() ?: "").crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.user_img),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
            )


            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = bookingDetails.userName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(end = 4.dp)
                )
                TextWithIcon(text = bookingDetails.rating.toString(), iconId = R.drawable.star)
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextWithIcon(text = playgroundName, iconId = R.drawable.soccerball)
            Spacer(modifier = Modifier.height(4.dp))
            TextWithIcon(
                text = "$bookingStartTime ${stringResource(id = R.string.to)} ${bookingEndTime ?: "55:55 AM"}",
                iconId = R.drawable.clock
            )

            Row {
                RatingBar(
                    value = userRate,
                    onValueChange = { userRate = it },
                    size = 44.dp,
                    spaceBetween = 1.dp,
                    painterEmpty = painterResource(id = R.drawable.unfilled_star),
                    painterFilled = painterResource(id = R.drawable.filled_star),
                    onRatingChanged = {},
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .align(Alignment.Top)
                )
            }
            MyButton(
                text = R.string.rate,
                onClick = onClickButtonRate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                )
            Spacer(modifier = Modifier.height(42.dp))

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(locale = "en")
@Composable
fun UserRatingSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    KhomasiGuardTheme {
        UserRatingSheet(
            bookingDetails = Booking(
                bookingTime = "2024-05-05T22:15:00",
                userName = "Ali Gamal",
                profilePicture = "profilePicture",
                rating = 4,
                cost = 50,
                email = "email",
                phoneNumber = "phoneNumber",
                bookingNumber = 1,
                confirmationCode = "2345",
                isCanceled = false,
                duration = 55
            ),
            playgroundName = "Playground Name",
            sheetState = sheetState,
            onDismissRequest = {},
            onClickButtonRate = {}
        )
    }
}