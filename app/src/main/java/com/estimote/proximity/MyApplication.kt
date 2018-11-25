package com.estimote.proximity

import android.app.Application
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials

/**
 * Shitalkumar
 */
class MyApplication : Application() {

    // static variables defined here
    companion object {
        val applicationTag = "shital-beacons-poc-40c"
        val appToken = "4c03a98755a169f224fc5682e7d34a3f"
    }

    val cloudCredentials = EstimoteCloudCredentials(applicationTag, appToken)
}
