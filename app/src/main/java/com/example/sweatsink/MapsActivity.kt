package com.example.sweatsink

import android.Manifest
import android.content.ContentProviderClient
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.Polyline


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val markers: MutableList<Marker> = ArrayList()
    private val polylines: MutableList<Polyline> = ArrayList()
    private var isCreatingRoute = true

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?)
        mapFragment!!.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize the "Create Route" button
        val createRouteButton = findViewById<Button>(R.id.buttonCreateRoute)
        createRouteButton.setOnClickListener {
            if (markers.size >= 2) {
                createWalkingRoute()
            } else {
                Toast.makeText(this, "Add at least two markers to create a route.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        getCurrentLocationUser()


//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnMapClickListener { latLng ->
            if (isCreatingRoute) {
                addMarker(latLng)
            }
        }
    }

    private fun getCurrentLocationUser(){
        if(ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this){
            location ->

            if(location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 7f))

            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng){
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        val marker = mMap.addMarker(markerOptions)
        markers.add(marker ?: return)
    }

    private fun addMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions().position(latLng)
        val marker = mMap.addMarker(markerOptions)
        markers.add(marker ?: return)
    }

    override fun onMarkerClick(p0: Marker) = false

    private fun createWalkingRoute() {
        val waypoints = markers.map { it.position }
        if (waypoints.size < 2) {
            return
        }

        // You should replace "YOUR_API_KEY" with your Google Maps API key
        val directionsUrl = getDirectionsUrl(waypoints, "walking", BuildConfig.MAPS_API_KEY)

        // Clear previous polylines
        clearPolylines()

        //use ok http to fetch a response from google
    }

    private fun getDirectionsUrl(waypoints: List<LatLng>, mode: String, apiKey: String): String {
        val origin = waypoints.first()
        val destination = waypoints.last()

        val waypointsString = waypoints
            .subList(1, waypoints.size - 1)
            .joinToString("|") { "${it.latitude},${it.longitude}" }

        return "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=${origin.latitude},${origin.longitude}" +
                "&destination=${destination.latitude},${destination.longitude}" +
                "&waypoints=$waypointsString" +
                "&mode=$mode" +
                "&key=$apiKey"
    }

    private fun clearPolylines() {
        for (polyline in polylines) {
            polyline.remove()
        }
        polylines.clear()
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


}