package com.estimote.proximity

import android.app.Application
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials

/**
 * Shitalkumar
 */
class ProximityApplication : Application() {

    // static variables defined here
    companion object {
        val applicationTag = "petrol-proximity"
        val appId = "proximity-poc-fvb"
        val appToken = "e8af2003046fab4293c91366017fc9ad"
    }

    val cloudCredentials = EstimoteCloudCredentials(appId, appToken)
}
