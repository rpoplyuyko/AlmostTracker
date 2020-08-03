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
                Location.convert(location.latitude, Location.FORMAT_MINUTES).toString(),
                Location.convert(location.longitude, Location.FORMAT_MINUTES).toString(),
                getDateStr()
            )
            itemViewModel.insert(item)
        } else {
            findAndSetText(id, "Location unknown")
        }
    }
}

fun Activity.getAddress(location: Location) : String {
    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses: List<Address> = geocoder?.getFromLocation(location.latitude.toDouble(), location.longitude.toDouble(), 1) as List<Address>

    val address = addresses.get(0).getAddressLine(0)

    return "$address"
}

fun Activity.getDateStr() : String {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    return sdf.format(Date())
}

fun Activity.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

