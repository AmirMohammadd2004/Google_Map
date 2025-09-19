package com.amir.google_map.ui.features.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.amir.google_map.R
import com.amir.google_map.util.MyScreen
import dev.burnoo.cokoin.navigation.getNavController
import kotlinx.coroutines.delay

@Composable
fun LoginPage() {

    val navigation = getNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .border(BorderStroke(10.dp ,Color.Red))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        LaunchedEffect(Unit) {
            delay(2500)
            navigation.navigate(MyScreen.HomePage.rote) {
                popUpTo(MyScreen.LoginPage.rote) {
                    inclusive = true
                }
            }
        }


        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                R.raw.map
            )

        )

        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxSize(),
            iterations = 2
        )


    }


}