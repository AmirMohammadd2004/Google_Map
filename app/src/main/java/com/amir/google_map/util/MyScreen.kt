package com.amir.google_map.util

sealed class MyScreen(val rote: String) {


    object LoginPage : MyScreen("LoginPage")

    object HomePage : MyScreen("HomePage")



}