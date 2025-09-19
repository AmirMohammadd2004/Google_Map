package com.amir.google_map.ui.features.home

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class HomeViewModel(

    context: Context

) : ViewModel() {


    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _location = MutableStateFlow<Location?>(null)
    val location = _location.asStateFlow()

    private var callBack: LocationCallback? = null


    private val _searchLocation = MutableStateFlow<LatLng?>(null)
    val searchLocation = _searchLocation.asStateFlow()

    fun setSearchLocation(latLng: LatLng) {
        _searchLocation.value = latLng
    }


    fun searchPlaceGeoCoder(context: Context , query: String ){

        if (query.isBlank()){
            _searchLocation.value = null
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val geocoder = Geocoder(context , Locale.getDefault())
                val addresses = geocoder.getFromLocationName(query , 1)
                val latLng = addresses?.firstOrNull()?.let {

                    LatLng(it.latitude , it.longitude)

                }
                withContext (Dispatchers.Main){
                    _searchLocation.value = latLng
                }

            }catch (e: Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    _searchLocation.value = null
                }

            }
        }

    }

//    fun searchPlace(context: Context, query: String, onResult: (LatLng?) -> Unit) {
//
//        val placesClient = Places.createClient(context)
//        val request = FindAutocompletePredictionsRequest.builder()
//            .setQuery(query)
//            .build()
//
//        placesClient.findAutocompletePredictions(request)
//            .addOnSuccessListener { response ->
//
//                if (response.autocompletePredictions.isNotEmpty()) {
//
//                    val placeId = response.autocompletePredictions[0].placeId
//
//                    val placeRequest = FetchPlaceRequest.builder(
//
//                        placeId,
//                        listOf(Place.Field.LAT_LNG)
//                    ).build()
//
//                    placesClient.fetchPlace(placeRequest)
//                        .addOnSuccessListener { placeResponse ->
//
//                            onResult(placeResponse.place.latLng)
//                        }
//
//                        .addOnFailureListener {
//
//                            onResult(null)
//                        }
//
//
//                } else {
//
//                    onResult(null)
//                }
//            }
//
//            .addOnFailureListener {
//                onResult(null)
//
//            }
//    }


    @SuppressLint("MissingPermission")
    fun getLocation() {

        fusedClient.lastLocation.addOnSuccessListener {

            it?.let { _location.value = it }

        }

        val request = LocationRequest.Builder(4000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(3F)
            .build()


        callBack = object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {

                result.lastLocation?.let { _location.value = it }

            }
        }
        fusedClient.requestLocationUpdates(request, callBack!!, null)
    }

    fun stopUpdates() {
        callBack?.let { fusedClient.removeLocationUpdates(it) }
    }

    override fun onCleared() {
        super.onCleared()
        stopUpdates()
    }


}