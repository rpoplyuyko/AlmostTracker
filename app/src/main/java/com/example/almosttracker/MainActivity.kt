package com.example.almosttracker

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startUpdatingLocation()
    }

    override fun onStart() {
        super.onStart()
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        lifecycleScope.launch {
            try {
                getLastKnownLocation()
            } catch (e: Exception) {
                findAndSetText(R.id.textView, "Unable to get location.")
                Log.d(TAG, "Unable to get location", e)
            }
        }
    }

    private suspend fun getLastKnownLocation() {
        try {
            val lastLocation = fusedLocationClient.awaitLastLocation()
            showLocation(R.id.textView, lastLocation)
        } catch (e: Exception) {
            findAndSetText(R.id.textView, "Unable to get location.")
            Log.d(TAG, "Unable to get location", e)
        }
    }

    private fun startUpdatingLocation() {
        fusedLocationClient.locationFlow()
            .conflate()
            .catch { e ->
                findAndSetText(R.id.textView, "Unable to get location.")
                Log.d(TAG, "Unable to get location", e)
            }
            .asLiveData()
            .observe(this, Observer { location ->
                showLocation(R.id.textView, location)
                Log.d(TAG, location.toString())
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate()
        }
    }
}

const val TAG = "KTX"