package com.company.khomasiguard.presentation.components

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.model.playground.Playground
import com.company.khomasiguard.domain.model.playground.PlaygroundInfo
import com.company.khomasiguard.domain.model.playground.PlaygroundX
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.convertToBitmap

@Composable
fun PlaygroundCard(
    playground: Playground,
    onViewPlaygroundClick: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    onClickActive: () -> Unit,
    onClickDeActive: () -> Unit
) {
    val playgroundData = remember(playground) {
        playground.playgroundInfo
    }
    val playgroundImage = remember(playground) {
        playgroundData.picture?.convertToBitmap()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onViewPlaygroundClick()
            }
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            PlaygroundImage(
                playgroundImage = playgroundImage,
                isBookable = playgroundData.playground.isBookable
            )

            Spacer(modifier = Modifier.height(8.dp))

            PlaygroundDefinition(playgroundData, context)

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            PlaygroundBookingInfo(playground)
        }
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.outline
        )
        PlaygroundActionButton(playgroundData, onClickActive, onClickDeActive)

    }
}

@Composable
fun PlaygroundActionButton(
    playgroundData: PlaygroundInfo,
    onClickActive: () -> Unit,
    onClickDeActive: () -> Unit
) {
    if (playgroundData.playground.isBookable) {
        MyOutlinedButton(
            text = R.string.deactivate, icon = R.drawable.power,
            onClick = onClickDeActive,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 11.dp)
        )
    } else {
        MyButton(
            text = R.string.activation,
            onClick = onClickActive,
            icon = R.drawable.power,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 11.dp)
        )
    }
}

@Composable
fun PlaygroundBookingInfo(playground: Playground) {
    val playgroundData = remember(playground) {
        playground.playgroundInfo
    }
    if (playgroundData.playground.isBookable) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.num_current_booking),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${playground.totalBookings} ${stringResource(R.string.booking)}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
    }
    else{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.num_expired_booking),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${playground.finishedBookings} ${stringResource(R.string.booking)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PlaygroundDefinition(playgroundData: PlaygroundInfo, context: Context) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = playgroundData.playground.name,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        TextWithIcon(
            text = context.getString(
                R.string.fees_per_hour,
                playgroundData.playground.feesForHour
            ),
            iconId = R.drawable.currencycircledollar,
        )

    }
    TextWithIcon(text = playgroundData.playground.address, iconId = R.drawable.mappin)

}

@Composable
fun PlaygroundImage(playgroundImage: Bitmap?, isBookable: Boolean) {
    Box {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(playgroundImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = { CircularProgressIndicator() },
            clipToBounds = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(
                    if (isBookable)
                        R.string.bookable
                    else
                        R.string.not_bookable
                ),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
            )
        }
    }
}

@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ar")
@Composable
fun PlaygroundCardPreview() {
    KhomasiGuardTheme {
        PlaygroundCard(
            playground = Playground(
                playgroundInfo = PlaygroundInfo(
                    playground = PlaygroundX(
                        id = 1,
                        name = "ZSC Playground",
                        feesForHour = 50,
                        address = "Nile Street, Zsc District, Cairo.",
                        isBookable = true
                    ),
                    picture = ""
                ),
                newBookings = 10,
                finishedBookings = 23,
                totalBookings = 33

            ),
            onViewPlaygroundClick = {},
            onClickActive = {},
            onClickDeActive = {}
        )
    }
}