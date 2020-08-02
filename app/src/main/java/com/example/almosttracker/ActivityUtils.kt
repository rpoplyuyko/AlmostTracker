package com.example.almosttracker

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView

fun Activity.findAndSetText(@IdRes id: Int, text: String) {
    findViewById<TextView>(id).text = text
}
//@IdRes id: Int, location: Location?
fun Activity.showLocation(@IdRes id: Int, count: Int, itemViewModel: ItemViewModel, location: Location?) {
    if (location != null) {
        val item = Item(count, "address",
            Location.convert(location.latitude, Location.FORMAT_MINUTES).toString(),
            Location.convert(location.longitude, Location.FORMAT_MINUTES).toString(),
            location.getTime().toString())
        itemViewModel.insert(item)
        findAndSetText(id, count.toString())
    } else {
        findAndSetText(id, "Location unknown")
    }
}

fun Activity.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

