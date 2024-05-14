package com.company.khomasiguard.presentation.login

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.login.GuardLoginResponse
import com.company.khomasiguard.presentation.components.MyButton
import com.company.khomasiguard.presentation.components.MyTextField
import com.company.khomasiguard.theme.Cairo
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.theme.darkSubText
import com.company.khomasiguard.theme.darkText
import com.company.khomasiguard.theme.lightSubText
import com.company.khomasiguard.theme.lightText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    isDark: Boolean = isSystemInDarkTheme(),
    uiState: StateFlow<LoginUiState>,
    loginState: StateFlow<DataState<GuardLoginResponse>>,
    updatePassword: (String) -> Unit,
    updateEmail: (String) -> Unit,
    login: () -> Unit,
    contactUs: () -> Unit,
    ourApp: () -> Unit,
) {
    var showLoading by remember { mutableStateOf(false) }
    val loginResponse = loginState.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val keyboardActions = KeyboardActions(
        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
        onDone = {
            keyboardController?.hide()
        }
    )

    val uiState =uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = loginResponse) {
        when (loginResponse) {
            is DataState.Loading -> {
                showLoading = true
                keyboardController?.hide()
            }

            is DataState.Success -> {
                showLoading = false
                Log.d("LoginScreen", "LoginScreen: ${loginResponse.data}")
            }

            is DataState.Error -> {
                showLoading = false
                Log.d("LoginScreen", "LoginScreen: ${loginResponse.message}")
            }

            is DataState.Empty -> {
                Log.d("LoginScreen", "LoginScreen: Empty")
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Image(
                painter =
                if (isSystemInDarkTheme())
                    painterResource(id = R.drawable.login_dark)
                else
                    painterResource(id = R.drawable.login_light),
                contentDescription = null,
                alignment = Alignment.Center,
            )
            Text(
                text = stringResource(id = R.string.fivefold_partner),
                style = MaterialTheme.typography.titleMedium,
                color = if (isDark) darkText else lightText,
                modifier = Modifier
                    .padding(top = 72.dp, start = 100.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.login),
            fontFamily = Cairo,
            fontWeight = FontWeight(700),
            fontSize = 28.sp,
            color = if (isDark) darkText else lightText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp, top = 30.dp),
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            value = uiState.email,
            onValueChange = updateEmail,
            label = R.string.email,
            keyBoardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            keyboardActions = keyboardActions,
        )
        Spacer(modifier = Modifier.height(24.dp))

        MyTextField(
            value = uiState.password,
            onValueChange = updatePassword,
            label = R.string.password,
            keyBoardType = KeyboardType.Password,
            keyboardActions = keyboardActions,
            imeAction = ImeAction.Done,
        )
        MyButton(
            onClick = {
                    Log.d("TestLogin", "LocalGuard: $login")
                    login()
            },
            text = R.string.login,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 78.dp)
                .height(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle()
                )
                {
                    append(stringResource(id = R.string.do_not_have_an_account)+" ")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    )
                ) {
                    append(stringResource(id = R.string.contact_us))
                    Modifier.clickable {
                        contactUs()
                    }
                }
                withStyle(
                    style = SpanStyle(

                    )
                )
                {
                    append(stringResource(id = R.string.partnership_text)+"\n")
                }
                withStyle(
                    style = SpanStyle(

                    )
                )
                {
                    append(stringResource(id = R.string.partners_app_message))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(id = R.string.our_app))
                    Modifier.clickable {
                      ourApp()
                    }
                }
            },
            color = if (isDark) darkSubText else lightSubText,
            style = MaterialTheme.typography.bodyMedium,

            )
        }
    }

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    KhomasiGuardTheme {
        val mockViewModel = MockLoginViewModel()
        LoginScreen(
            updatePassword = mockViewModel::updatePassword,
            updateEmail = mockViewModel::updateEmail,
            login = mockViewModel::login,
            loginState = mockViewModel.loginState,
            uiState = mockViewModel.uiState,
            contactUs = mockViewModel::contactUs,
            ourApp = mockViewModel::ourApp
        )
    }
}