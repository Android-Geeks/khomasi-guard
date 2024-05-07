package com.company.khomasiguard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.company.app.R
import com.company.khomasiguard.util.convertToBitmap

@Composable
fun UserDefinition(
    userName: String,
    profileImg: String?,
    userRate: String,
    onClickCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(profileImg?.convertToBitmap() ?: "").crossfade(true).build(),
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
                    .background(MaterialTheme.colorScheme.background),
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
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
