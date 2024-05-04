package com.company.khomasiguard.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.company.app.R

val Cairo = FontFamily(
    Font(R.font.cairo, FontWeight.Normal),
    Font(R.font.cairo_semibold, FontWeight.SemiBold),
    Font(R.font.cairo_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(700),
        fontSize = 18.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(500),
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(700),
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(700),
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
    )
)

/*
Display
    As the largest text on the screen, display styles are reserved for short, important text or numerals. They work best on large screens.

Headline
    Headlines are best-suited for short, high-emphasis text on smaller screens. These styles can be good for marking primary passages of text or important regions of content.

Title
    Titles are smaller than headline styles, and should be used for medium-emphasis text that remains relatively short.

Body
    Body styles are used for longer passages of text in your app.

Label
    Label styles are smaller, utilitarian styles, used for things like the text inside components or for very small text in the content body, such as captions.
 */