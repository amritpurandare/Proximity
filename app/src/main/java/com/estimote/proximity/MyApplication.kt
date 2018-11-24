package com.estimote.proximity

import android.app.Application
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials

/**
 * Shitalkumar
 */
class MyApplication : Application() {

    val cloudCredentials =  EstimoteCloudCredentials("shital-beacons-poc-40c", "4c03a98755a169f224fc5682e7d34a3f")
}
