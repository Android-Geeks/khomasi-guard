package com.company.khomasiguard.presentation.components


import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.app.R
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.convertToBitmap


@Composable
fun ShortBookingCard(
    modifier: Modifier = Modifier,
    userName: String,
    playgroundName: String,
    userRate: String,
    userPicture: String?,
    bookingPrice: Double,
    bookingDate: String,
    bookingDuration: String,
    context: Context = LocalContext.current,
    onClickViewBooking: () -> Unit,
    onClickCall: () -> Unit
) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            UserDefinition(
                userName = userName,
                userImg = userPicture,
                userRate = userRate,
                onClickCall = onClickCall
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            BookingCardContent(
                playgroundName = playgroundName,
                bookingDate = bookingDate,
                bookingDuration = bookingDuration,
                bookingPrice = bookingPrice,
                context = context,
                onClickViewBooking = onClickViewBooking
            )
        }

    }
}

@Composable
fun UserDefinition(
    userName: String,
    userImg: String?,
    userRate: String,
    onClickCall: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(userImg?.convertToBitmap() ?: "").crossfade(true).build(),
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

        Column(
            Modifier.padding(start = 4.dp), verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier

            )

            TextWithIcon(text = userRate, iconId = R.drawable.star)
        }
        Spacer(modifier = Modifier.weight(1f))

        TextWithIcon(
            text = stringResource(id = R.string.call),
            iconId = R.drawable.phone,
            color = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.titleLarge,
            textDecoration = TextDecoration.Underline,
            onClick = onClickCall
        )

    }
}

@Composable
fun BookingCardContent(
    playgroundName: String,
    bookingDate: String,
    bookingDuration: String,
    bookingPrice: Double,
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
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
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
        TextWithIcon(text = bookingDuration, iconId = R.drawable.clock)

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
            userName = "Ahmed",
            userRate = "4.5",
            userPicture = "",
            bookingPrice = 100.0,
            bookingDate = "2022-10-10",
            bookingDuration = "2 hours",
            playgroundName = "Playground Name",
            onClickViewBooking = {},
            onClickCall = {}
        )
    }
}
