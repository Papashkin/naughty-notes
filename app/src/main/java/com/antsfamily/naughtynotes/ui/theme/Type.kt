package com.antsfamily.naughtynotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.antsfamily.naughtynotes.R

object FontSize {
    val Caption = 12.sp
    val Body2 = 14.sp
    val Body1 = 16.sp
    val H6 = 20.sp
    val H5 = 24.sp
    val H4 = 32.sp
    val H3 = 48.sp
    val H2 = 60.sp
    val H1 = 96.sp
}

val lato = FontFamily(
    Font(R.font.lato_regular, weight = FontWeight.Medium),
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lato_thin, weight = FontWeight.Thin),
    Font(R.font.lato_light, weight = FontWeight.Light),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_black, weight = FontWeight.Black),
)

val montserrat = FontFamily(
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_regular, weight = FontWeight.Medium),
    Font(R.font.montserrat_thin, weight = FontWeight.Thin),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_black, weight = FontWeight.Black),
)

val appTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.H1
    ),
    headlineMedium = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.H2
    ),
    headlineSmall = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.H3
    ),
    titleLarge = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.H4
    ),
    titleMedium = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.H5
    ),
    titleSmall = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.H6
    ),
    bodyLarge = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.H6
    ),
    bodyMedium = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.Body1
    ),
    bodySmall = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = FontSize.Body2
    ),
    labelLarge = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.Body1
    ),
    labelMedium = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.Body2
    ),
    labelSmall = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = FontSize.Caption
    ),
)