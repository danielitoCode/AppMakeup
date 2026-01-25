package com.elitec.appmakeup.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.abrilFatface_regular
import appmakeup.composeapp.generated.resources.poppins_black
import appmakeup.composeapp.generated.resources.poppins_bold
import appmakeup.composeapp.generated.resources.poppins_extraBold
import appmakeup.composeapp.generated.resources.poppins_italic
import appmakeup.composeapp.generated.resources.poppins_light
import appmakeup.composeapp.generated.resources.poppins_medium
import appmakeup.composeapp.generated.resources.poppins_regular
import appmakeup.composeapp.generated.resources.poppins_semiBold
import org.jetbrains.compose.resources.Font

val AppTypography = Typography()

@Composable
fun abrilFatfaceFontFamily() = FontFamily(
    Font(Res.font.abrilFatface_regular, weight = FontWeight.Normal)
)

@Composable
fun poppinsFontFamily() = FontFamily(
    Font(Res.font.poppins_regular, weight = FontWeight.Normal),
    Font(Res.font.poppins_italic, weight = FontWeight.Medium),
    Font(Res.font.poppins_medium, weight = FontWeight.Medium),
    Font(Res.font.poppins_bold, weight = FontWeight.Bold),
    Font(Res.font.poppins_extraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.poppins_light, weight = FontWeight.Light),
    Font(Res.font.poppins_semiBold, weight = FontWeight.SemiBold),
    Font(Res.font.poppins_black, weight = FontWeight.Black),
)
