package com.amir.google_map.ui.features.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.amir.google_map.util.MyScreen
import com.amir.google_map.util.NetworkChecker
import com.amir.google_map.util.RequestPermission
import com.amir.google_map.util.TryToNetwork
import com.amir.google_map.util.isLocationEnabled
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

@Composable
fun HomePage() {

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<HomeViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    val permissionGranted = remember { mutableStateOf(false) }
    val startFind = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val searchLocation = viewModel.searchLocation.collectAsState().value
    val locationUser = viewModel.location.collectAsState().value

    val hasRequestedLocation = remember { mutableStateOf(false) }

    if (NetworkChecker(context).isInternetConnected) {

        RequestPermission(
            permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
            onPermissionGranted = {
                permissionGranted.value = true
            }
        )

        // فقط یکبار لوکیشن بگیر
        if (permissionGranted.value && !hasRequestedLocation.value) {
            LaunchedEffect(Unit) {
                viewModel.getLocation()
                hasRequestedLocation.value = true
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 57.dp, top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ردیف سرچ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp , start = 16.dp , end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Search Location") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton({
                    keyboardController?.hide()

                    viewModel.searchPlaceGeoCoder(context ,searchQuery.value)


                }) {
                    Icon(
                        Icons.Default.Search,
                        modifier = Modifier.size(30.dp),
                        contentDescription = "Search"
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {

                MapGoogle(
                    userLat = if (startFind.value) locationUser?.latitude else null,
                    userLng = if (startFind.value) locationUser?.longitude else null,
                    searchLocation = searchLocation
                )

                // دکمه موقعیت فعلی
                val buttonColor = if (startFind.value) Color.Black else Color.White
                Surface(
                    modifier = Modifier
                        .padding(bottom = 40.dp, start = 10.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    color = buttonColor
                ) {
                    IconButton({
                        if (isLocationEnabled(context)) {
                            startFind.value = !startFind.value
                        } else {
                            Toast.makeText(context, "Please turn on location", Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Icon(
                            Icons.Default.LocationOn,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Red,
                            contentDescription = null
                        )
                    }
                }
            }
        }

    } else {
        TryToNetwork(
            onTryClick = {
                if (NetworkChecker(context).isInternetConnected) {
                    navigation.navigate(MyScreen.HomePage.rote) {
                        popUpTo(MyScreen.LoginPage.rote) { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapGoogle(userLat: Double?, userLng: Double?, searchLocation: LatLng?) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(35.6892, 51.3890), 10f)
    }

    LaunchedEffect(searchLocation, userLat, userLng) {
        val target = searchLocation ?: if (userLat != null && userLng != null) LatLng(userLat, userLng) else null
        target?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(it, 18f),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium),
        cameraPositionState = cameraPositionState
    ) {
        userLat?.let { lat ->
            userLng?.let { lng ->
                Marker(
                    state = MarkerState(LatLng(lat, lng)),
                    title = "Your Location"
                )
            }
        }

        searchLocation?.let {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = "Search Location"
            )
        }
    }
}

