package com.example.almosttracker

import android.app.Activity
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

fun Activity.findAndSetText(@IdRes id: Int, text: String) {
    findViewById<TextView>(id).text = text
}
//@IdRes id: Int, location: Location?
fun Activity.showLocation(@IdRes id: Int, flag: Boolean, itemViewModel: ItemViewModel, location: Location?) {
    if (flag) {
        if (location != null) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val item = Item(
                "address",
                Location.convert(location.latitude, Location.FORMAT_MINUTES).toString(),
                Location.convert(location.longitude, Location.FORMAT_MINUTES).toString(),
                currentDate
            )
            itemViewModel.insert(item)
        } else {
            findAndSetText(id, "Location unknown")
        }
    }
}

fun Activity.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

