package com.company.khomasiguard.presentation.components


import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.app.R
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.convertToBitmap


@Composable
fun PlaygroundBookingCard(
    userName: String,
    playgroundName: String,
    userRate: String,
    userPicture: String?,
    bookingPrice: Int,
    bookingDate: String,
    bookingDuration: String,
    modifier: Modifier = Modifier,
    verificationCode: String
) {

    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(userPicture?.convertToBitmap() ?: "").crossfade(true).build(),
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
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
            )

        }

    }

}

@Composable
fun BookingCardContent(
    playgroundName: String,
    bookingDate: String,
    booDuration: String,
    bookingPrice: Double,
    context: Context = LocalContext.current,
    onClickViewBooking: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, bottom = 16.dp)
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
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.small,

                ) {
                TextWithIcon(
                    text = context.getString(R.string.fees_for_booking, bookingPrice),
                    iconId = R.drawable.currencycircledollar,
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(
            text = bookingDate,
            iconId = R.drawable.calendar,
        )
        TextWithIcon(text = booDuration, iconId = R.drawable.clock)

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 16.dp, bottom = 13.dp),
            color = MaterialTheme.colorScheme.outline
        )
        MyButton(text = R.string.view_booking, onClick = { onClickViewBooking() })
    }
}

@Composable
fun TextWithIcon(
    text: String,
    @DrawableRes iconId: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
private fun BookingCardPreview() {
    KhomasiGuardTheme {
//        PlaygroundBookingCard(
//            userName = "Ahmed",
//            userRate = "4.5",
//            userPicture = "",
//            bookingPrice = 100,
//            bookingDate = "2022-10-10",
//            bookingDuration = "2 hours",
//            verificationCode = "1234",
//            playgroundName = "Playground Name"
//        )
        BookingCardContent(
            playgroundName = "Playground Name",
            bookingDate = "2022-10-10",
            booDuration = "7 AM - 9 AM",
            bookingPrice = 100.0,
            onClickViewBooking = {}
        )
    }
}
