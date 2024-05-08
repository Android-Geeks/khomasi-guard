package com.company.khomasiguard.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.company.khomasiguard.R
import com.company.khomasiguard.theme.KhomasiGuardTheme

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    keyBoardType: KeyboardType,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Done,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    var isPassword by remember { mutableStateOf(keyBoardType == KeyboardType.Password) }
    Column {
        Text(
            text = stringResource(id = label),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,
            trailingIcon = if (keyBoardType == KeyboardType.Password) {
                {
                    IconButton(onClick = { isPassword = !isPassword }) {
                        Icon(
                            painter = painterResource(id = if (isPassword) R.drawable.outline_visibility_off_24 else R.drawable.outline_visibility_24),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else null,
            supportingText = supportingText,
            modifier = modifier
                .fillMaxWidth(),
            placeholder = { Text(text = placeholder) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyBoardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            visualTransformation = if (isPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            textStyle = TextStyle(
                textDirection = if (keyBoardType == KeyboardType.Phone &&
                    LocalLayoutDirection.current == LayoutDirection.Rtl
                )
                    TextDirection.Ltr
                else
                    TextDirection.Content
            ),
            isError = isError
        )
    }
}

@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES, locale = "en")
@Preview(name = "light", uiMode = UI_MODE_NIGHT_NO, locale = "ar")
@Composable
fun TextFieldPreview() {
    KhomasiGuardTheme {
        MyTextField(
            value = "Zeyad",
            onValueChange = { },
            label = R.string.email,
            placeholder = "Enter your email",
            keyBoardType = KeyboardType.Phone,
            modifier = Modifier
        )
    }
}

