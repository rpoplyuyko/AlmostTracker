package com.example.almosttracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var itemViewModel: ItemViewModel
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ItemListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemViewModel.allWords.observe(this, Observer { items ->
            // Update the cached copy of the words in the adapter.
            items?.let { adapter.setItems(it) }
        })

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startUpdatingLocation()

        fab.setOnClickListener() {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0)
        }
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
            showLocation(R.id.textView, count, itemViewModel, lastLocation)
            count++
            findAndSetText(R.id.textView, (recyclerView.layoutManager!!.itemCount + 1).toString())
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
                showLocation(R.id.textView, count, itemViewModel, location)
                count++
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