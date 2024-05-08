package com.company.khomasiguard.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.khomasiguard.R
import com.company.khomasiguard.theme.Cairo
import com.company.khomasiguard.theme.KhomasiGuardTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWarning(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    userName : String,
    onClickCancel: () -> Unit,
    context: Context = LocalContext.current,
    mainTextId : Int,
    subTextId : Int,
    mainButtonTextId : Int,
    subButtonTextId : Int
) {
    MyModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp, top = 8.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            ),

                            )
                        {
                            append(stringResource(id = mainTextId) + "\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = Cairo,
                                fontWeight = FontWeight(500),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            append(context.getString(subTextId,userName))
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MyOutlinedButton(
                    text = subButtonTextId,
                    onClick = onDismissRequest,
                    modifier = Modifier.weight(1f)
                )
                MyButton(
                    text = mainButtonTextId,
                    onClick = {
                        onClickCancel()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BSWarningPreview() {
    KhomasiGuardTheme {
        val sheetState = rememberModalBottomSheetState()
        BottomSheetWarning(
            userName = "Ali Gamal",
            sheetState = sheetState,
            onDismissRequest = {}, onClickCancel = {},
            mainTextId = R.string.confirm_cancel_booking,
            subTextId = R.string.action_will_cancel_booking,
            mainButtonTextId = R.string.cancel_booking,
            subButtonTextId = R.string.back)
    }
}
