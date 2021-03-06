package com.estimote.proximity.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.estimote.proximity.BuildConfig
import com.estimote.proximity.MainActivity
import com.estimote.proximity.ProximityApplication
import com.estimote.proximity_sdk.api.ProximityObserver
import com.estimote.proximity_sdk.api.ProximityObserverBuilder
import com.estimote.proximity_sdk.api.ProximityZoneBuilder
import java.util.*

/**
 * Shitalkumar
 */
class ProximityContentManager(private val context: Context) {
    private var D: Boolean = BuildConfig.DEBUG
    private var proximityObserverHandler: ProximityObserver.Handler? = null
    private val TAG: String = "ProximityContentManager"
    private var isStopCalled: Boolean = false

    fun start() {
        val proximityObserver = ProximityObserverBuilder(context, (context.applicationContext as ProximityApplication).cloudCredentials)
                //.withTelemetryReportingDisabled()
                .withLowLatencyPowerMode() // This is most reliable mode but may drain the battery
                .onError { throwable ->
                    if (D && !isStopCalled) {
                        Log.e(TAG, "proximity observer error: $throwable")
                        Toast.makeText(context, "proximity observer error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                        (context as MainActivity).showHideLoading(false)
                    }
                }
                .build()

        val zone = ProximityZoneBuilder()
                .forTag(ProximityApplication.applicationTag) // can change the tag
                .inFarRange()
                .onEnter {

                    if (D) {
                        Log.d(TAG, "user entered into proximity zone")
                        Toast.makeText(context, "user entered into proximity zone", Toast.LENGTH_SHORT).show()
                    }
                }
                .onExit {
                    if (D) {
                        Log.d(TAG, "user exited the proximity zone")
                        Toast.makeText(context, "user exited the proximity zone", Toast.LENGTH_SHORT).show()
                    }
                }
                .onContextChange { contexts ->
                    val nearbyContent = ArrayList<ProximityContent>(contexts.size)
                    for (context in contexts) {
                        val title: String = context.attachments["shital-beacons-poc-40c/title"]
                                ?: "unknown"
                        val subtitle = Utils.getShortIdentifier(context.deviceId)
                        nearbyContent.add(ProximityContent(title, subtitle))
                    }

                    (context as MainActivity).setNearbyContent(nearbyContent)
                }
                .build()
        proximityObserverHandler = proximityObserver.startObserving(zone)
    }

    fun stop() {
        isStopCalled = true
        proximityObserverHandler?.stop()
    }
}
