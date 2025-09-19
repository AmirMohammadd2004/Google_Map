package com.amir.google_map.ui

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amir.google_map.di.myModules
import com.amir.google_map.them.Google_MapTheme
import com.amir.google_map.ui.features.home.HomePage
import com.amir.google_map.ui.features.login.LoginPage
import com.amir.google_map.util.MyScreen
import com.google.android.libraries.places.api.Places
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext
import com.amir.google_map.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (!Places.isInitialized()){
//            Places.initialize( applicationContext , getString(R.string.google_maps_key) )
//        }

        enableEdgeToEdge()
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        setContent {

            Koin(
                appDeclaration = {

                    androidContext(this@MainActivity)
                    modules(myModules)

                }){
                Google_MapTheme {

                    UiPage()

                }
            }
        }
    }
}



@Composable
fun UiPage(){

    val navigation = rememberNavController()

    KoinNavHost(
        navController = navigation ,
        startDestination = MyScreen.LoginPage.rote
    ){

        composable(
            route = MyScreen.LoginPage.rote
        ) {

            LoginPage()
        }

        composable (
            route = MyScreen.HomePage.rote
        ){

            HomePage()

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Google_MapTheme {
    }
}