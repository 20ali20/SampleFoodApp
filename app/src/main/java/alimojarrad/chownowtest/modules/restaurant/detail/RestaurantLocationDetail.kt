package alimojarrad.chownowtest.modules.restaurant.detail

import alimojarrad.chownowtest.R
import alimojarrad.chownowtest.models.Location
import alimojarrad.chownowtest.modules.common.views.MapMarkerFactory
import alimojarrad.chownowtest.services.navigation.BaseDialogFragment
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.RelativeLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sidecarhealth.modules.common.Utils
import kotlinx.android.synthetic.main.fragment_restaurant_location_detail.*
import timber.log.Timber


class RestaurantLocationDetail : BaseDialogFragment(), OnMapReadyCallback {


    companion object {
        const val dataKey = "DATAKEY"
        fun newInstance(location: Location): RestaurantLocationDetail {
            val fragment = RestaurantLocationDetail()
            val bundle = Bundle()
            bundle.putSerializable(dataKey, location)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var map: GoogleMap? = null
    private val mapMarkerFactory = MapMarkerFactory()
    private var currentMarker: Marker? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var root = RelativeLayout(activity)
        root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        // creating the fullscreen dialog
        var dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        val metrics = DisplayMetrics()
        (context!!.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.getMetrics(metrics)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window.attributes.gravity = Gravity.BOTTOM
        dialog.window.setLayout((metrics.widthPixels - 40), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        // Add back button itemListener
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                dismiss()
                return@OnKeyListener true
            }
            false
        })
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant_location_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rld_mapView.onCreate(savedInstanceState)
        rld_mapView.getMapAsync(this)
        setupViews()
        setupInteraction()
    }


    override fun onResume() {
        super.onResume()
        rld_mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        rld_mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.dialog_animation_slideupdown)
        rld_mapView.onStart()
    }

    override fun onDestroy() {
        map?.clear()
        rld_mapView?.onDestroy()
        currentMarker = null
        super.onDestroy()

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        try {

            map = googleMap
            map?.uiSettings?.isMapToolbarEnabled = false
            retrieveLocation()?.let {
                it.address?.latitude?.let { lat ->
                    it.address?.longitude?.let { lon ->
                        currentMarker = map?.addMarker(
                            MarkerOptions()
                                .position(LatLng(lat.toDouble(), lon.toDouble()))
                                .icon(mapMarkerFactory.getRoundMarker(context!!))
                        )
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(currentMarker!!.position.latitude, currentMarker!!.position.longitude), 12f
                            )
                        )
                    }
                }
            }
            map?.setOnMarkerClickListener { marker ->
                Utils.application.callGoogleMaps(
                    context!!,
                    marker.position.latitude.toString(),
                    marker.position.longitude.toString()
                )
                true

            }

        } catch (e: java.lang.NullPointerException) {
            Timber.e(e)
        }
    }

    private fun retrieveLocation(): Location? {
        return arguments?.getSerializable(dataKey) as? Location
    }

    private fun setupViews() {
        retrieveLocation()?.let {
            rld_title.text = it.short_name ?: "N/A"
            rld_phone.text = Utils.conversion.getFormattedPhoneNumber(it.phone)
            rld_address.text = Utils.conversion.showAddress(rld_address, it.address)
        }
    }

    private fun setupInteraction() {
        rld_close.setOnClickListener {
            dismiss()
        }

        rld_call.setOnClickListener {
            retrieveLocation()?.phone?.let {
                Utils.application.callIntent(context!!,it)
            }
        }
        rld_navigation.setOnClickListener {
            currentMarker?.let {
                Utils.application.callNavigation(
                    context!!,
                    it.position.latitude.toString(),
                    it.position.longitude.toString()
                )
            }
        }

    }


}
