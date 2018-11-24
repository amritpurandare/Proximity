package com.estimote.proximity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.estimote.proximity.utils.ProximityContent
import com.estimote.proximity.utils.ProximityContentAdapter
import com.estimote.proximity.utils.ProximityContentManager
import dmax.dialog.SpotsDialog
import java.lang.Exception

/**
 * Shitalkumar
 */
class MainActivity : AppCompatActivity()
{
    private var proximityContentManager: ProximityContentManager? = null
    private var proximityContentAdapter: ProximityContentAdapter? = null
    private var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Initialise dialog
         */
        mAlertDialog = SpotsDialog(this, R.style.SpotsDialogStyle)

        /**
         * Initialize grid view with adapter
         */
        proximityContentAdapter = ProximityContentAdapter(this)
        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = proximityContentAdapter

        /**
         * check for permissions
         */
        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        {
                            Log.d("app", "Permission provided")
                            Toast.makeText(this, "Permission provided", Toast.LENGTH_SHORT).show()

                            /**
                             * Start searching
                             */
                            startProximityContentManager()
                        },
                        { requirements ->
                            Log.e("app", "Permission not provided: " + requirements)
                            Toast.makeText(this, "Permission not provided: " + requirements, Toast.LENGTH_SHORT).show()
                        }
                        ,
                        { throwable ->
                            Log.e("app", "Permission Error: " + throwable)
                            Toast.makeText(this, "Permission Errorr: " + throwable, Toast.LENGTH_SHORT).show()
                        })
    }

    /**
     *
     */
    private fun startProximityContentManager()
    {
        showHideLoading(true)
        proximityContentManager = ProximityContentManager(this)
        proximityContentManager?.start()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        proximityContentManager?.stop()
    }

    fun setNearbyContent(nearbyContent: List<ProximityContent>)
    {
        showHideLoading(false)
        proximityContentAdapter?.setNearbyContent(nearbyContent)
        proximityContentAdapter?.notifyDataSetChanged()
    }

    /**
     *
     */
    fun showHideLoading(isShow: Boolean)
    {
        try {
            if (mAlertDialog != null) {
                mAlertDialog?.setCancelable(false)

                if (isShow)
                    mAlertDialog?.show()
                else
                    mAlertDialog?.dismiss()
            }
        }
        catch(exception: Exception)
        {
           exception.printStackTrace()
        }
    }
}
