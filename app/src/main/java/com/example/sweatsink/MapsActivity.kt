package com.example.sweatsink

import android.Manifest
import android.content.ContentProviderClient
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sweatsink.Common.Common
import com.example.sweatsink.Model.MyPlaces
import com.example.sweatsink.Remote.IGoogleAPIServices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val markers: MutableList<Marker> = ArrayList()
    private val polylines: MutableList<Polyline> = ArrayList()
    private var isCreatingRoute = true

    private var latitude: Double=0.toDouble()
    private var longitude: Double=0.toDouble()

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    lateinit var mService:IGoogleAPIServices

    internal lateinit var currentPlaces: MyPlaces

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?)
        mapFragment!!.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mService = Common.googleAPIServices

        // Initialize the "Create Route" button
        val createRouteButton = findViewById<Button>(R.id.buttonCreateRoute)
        createRouteButton.setOnClickListener {
            if (markers.size >= 2) {
                createWalkingRoute()
            } else {
                Toast.makeText(this, "Add at least two markers to create a route.", Toast.LENGTH_SHORT).show()
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_hospital -> nearByPlace("hospital")
                R.id.action_market -> nearByPlace("market")
                R.id.action_restaurant -> nearByPlace("restaurant")
                R.id.action_park -> nearByPlace("park")

            }
            true
        }
    }

   private fun bitmapDescriptorFromVector(context: retrofit2.Callback<MyPlaces>, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(this, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    private fun nearByPlace(typePlace: String){
        //Clear all marker on map
        mMap.clear()

        getCurrentLocationUser()
        latitude = lastLocation.latitude
        longitude = lastLocation.longitude

        val url = getUrl(latitude,longitude,typePlace)

        mService.getNearbyPlaces(url)
            .enqueue(object : retrofit2.Callback<MyPlaces>{
                override fun onResponse(
                    call: retrofit2.Call<MyPlaces>,
                    response: retrofit2.Response<MyPlaces>
                ) {
                    currentPlaces = response.body()!!


                    if(response.isSuccessful){
                        for(i in 0 until (response.body()!!.results!!.size))
                        {
                            val markerOptions=MarkerOptions()
                            val googlePlaces = response.body()!!.results!![i]
                            val lat = googlePlaces.geometry!!.location!!.lat
                            val lng = googlePlaces.geometry!!.location!!.lng
                            val placeName  = googlePlaces.name
                            val latLng = LatLng(lat,lng)

                            markerOptions.position(latLng)
                            markerOptions.title(placeName)
                            if(typePlace.equals ("hospital"))
                                markerOptions.icon(bitmapDescriptorFromVector(this,R.drawable.ic_hospital))
                            else if(typePlace.equals("market"))
                                markerOptions.icon(bitmapDescriptorFromVector(this,R.drawable.ic_market))
                            else if(typePlace.equals("restaurant"))
                                markerOptions.icon(bitmapDescriptorFromVector(this,R.drawable.ic_restaurant))
                            else if(typePlace.equals("park"))
                                markerOptions.icon(bitmapDescriptorFromVector(this,R.drawable.ic_park))
                            else
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))


                            //add marker to map
                            mMap.addMarker(markerOptions)
                            //move camera
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))

                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(baseContext,"" + t!!.message,Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun getUrl(latitude: Any, longitude: Any, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyBnYzbe5se2fIVoIkBbm3XufH8FPiwhIy0")

        Log.d("URL_DEBUG",googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        getCurrentLocationUser()

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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 15f))

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

        //call okHTTPGetRequest(directionsUrl)
    }

    private fun clearPolylines() {
        for (polyline in polylines) {
            polyline.remove()
        }
        polylines.clear()
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

    private fun okHTTPGetRequest(url: String, callback:(String) -> Unit){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failure", e)
            }
            override fun onResponse(call: Call, response: Response) {
                /*
                val body=response.body?.string()
                if (body != null) {
                    println(body)
                }
                else{
                    println("no reply")
                }
                try {
                    var jsonObject= JSONObject(body)
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    val textResult=jsonArray.getJSONObject(0).getString("text")
                    callback(textResult)
                }
                catch(e: JSONException){
                    val message="OpenAI API error!"
                    println(message)
                    callback(message)
                } */
            }
        })
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