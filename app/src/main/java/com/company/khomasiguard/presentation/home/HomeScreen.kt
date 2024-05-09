package com.company.khomasiguard.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.khomasiguard.R
import com.company.khomasiguard.theme.Cairo

@Composable
fun HomeScreen(
    isDark :Boolean = isSystemInDarkTheme()

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
       Column (
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier
               .height(247.dp)
               .background(MaterialTheme.colorScheme.background)

       ){
           Row(
               verticalAlignment = Alignment.Top,
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Text(
                   text = buildAnnotatedString {
                       withStyle(
                           style = SpanStyle(
                               fontFamily = Cairo,
                               fontWeight = FontWeight(500),
                               fontSize = 16.sp
                           )
                       )
                       {
                           append(stringResource(id = R.string.hello_captain) + "\n")
                       }
                       withStyle(
                           style = SpanStyle(
                               fontFamily = Cairo,
                               fontWeight = FontWeight(500),
                               fontSize = 14.sp
                           )
                       ) {
                           append(stringResource(id = R.string.hello_captain))
                       }

                   })
               Box(
                   modifier = Modifier
                       .height(32.dp)
                       .background(
                           MaterialTheme.colorScheme.background
                       )){
                             Row {
                                 Icon(painter = painterResource(id = R.drawable.signout), contentDescription ="" )
                                 //Text(text = stringResource(id = R.string.c))
                             }
               }
           }
           Row() {
               Box {

               }
               Box {

               }

           }

       }


    }
}