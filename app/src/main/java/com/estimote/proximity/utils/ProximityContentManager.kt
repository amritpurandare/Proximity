package com.estimote.proximity.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.estimote.proximity.MainActivity
import com.estimote.proximity.MyApplication
import com.estimote.proximity_sdk.api.ProximityObserver
import com.estimote.proximity_sdk.api.ProximityObserverBuilder
import com.estimote.proximity_sdk.api.ProximityZoneBuilder
import java.util.*

/**
 * Shitalkumar
 */
class ProximityContentManager(private val context: Context)
{
    private var proximityObserverHandler: ProximityObserver.Handler? = null

    fun start()
    {
        val proximityObserver = ProximityObserverBuilder(context, (context.applicationContext as MyApplication).cloudCredentials)
                //.withTelemetryReportingDisabled()
                .withLowLatencyPowerMode()
                .onError { throwable ->
                    Log.e("app", "proximity observer error: $throwable")
                    Toast.makeText(context, "proximity observer error: $throwable", Toast.LENGTH_SHORT).show()
                }
                .build()

        val zone = ProximityZoneBuilder()
                .forTag("shital-beacons-poc-40c") // can change the tag
                .inFarRange()
                .onContextChange { contexts ->
                    val nearbyContent = ArrayList<ProximityContent>(contexts.size)
                    for (context in contexts) {
                        val title: String = context.attachments["shital-beacons-poc-40c/title"] ?: "unknown"
                        val subtitle = Utils.getShortIdentifier(context.deviceId)
                        nearbyContent.add(ProximityContent(title, subtitle))
                    }

                    (context as MainActivity).setNearbyContent(nearbyContent)
                }
                .build()

        proximityObserverHandler = proximityObserver.startObserving(zone)
    }

    fun stop() {
        proximityObserverHandler?.stop()
    }
}
