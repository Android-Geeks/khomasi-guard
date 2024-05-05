package com.company.khomasiguard.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    buttonEnable: Boolean = true,
    color: ButtonColors? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        enabled = buttonEnable,
        colors = color ?: ButtonDefaults.buttonColors()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != 0) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    }
}


@Composable
fun MyTextButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isUnderlined: Boolean = true,
    textSize: TextUnit = 14.sp
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = text),
            textAlign = TextAlign.Center,
            textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
            style = MaterialTheme.typography.titleLarge,
            fontSize = textSize
        )
    }
}


@Composable
fun MyOutlinedButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = 0,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != 0) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = stringResource(id = text),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}