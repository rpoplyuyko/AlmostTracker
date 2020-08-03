package com.example.almosttracker

import android.app.Activity
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import java.util.*

fun Activity.findAndSetText(@IdRes id: Int, text: String) {
    findViewById<TextView>(id).text = text
}
//@IdRes id: Int, location: Location?
fun Activity.showLocation(@IdRes id: Int, flag: Boolean, itemViewModel: ItemViewModel, location: Location?) {
    if (flag) {
        if (location != null) {
            val item = Item(
                getAddress(location),
                getCoordinates(location, true),
                getCoordinates(location, false),
                getDateStr()
            )
            itemViewModel.insert(item)
        } else {
            findAndSetText(id, "Location unknown")
        }
    }
}

fun Activity.getCoordinates(location: Location, flag: Boolean) : String {
    if (flag) {
        return (location.latitude.toDouble().toString() + "°")
    } else {
        return (location.longitude.toDouble().toString() + "°")
    }
}

fun Activity.getAddress(location: Location) : String {
    val geoCoder = Geocoder(this, Locale.getDefault())
    val addresses: List<Address> = geoCoder.getFromLocation(location.latitude.toDouble(), location.longitude.toDouble(), 1) as List<Address>

    val address = addresses.get(0).getAddressLine(0)

    return address.toString()
}

fun Activity.getDateStr() : String {
    val sdf = SimpleDateFormat("dd/M/yyyy kk:mm:ss")
    return sdf.format(Date())
}

fun Activity.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

