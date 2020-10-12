package com.example.holamon

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FullMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var mapReady: Boolean = false

    private var circle1Activate: Boolean = false
    private var circle10Activate: Boolean = false
    private var circle100Activate: Boolean = false

    private lateinit var  circle1: Circle
    private lateinit var  circle10: Circle
    private lateinit var  circle100: Circle

    val barcelona = LatLng(41.3851, 2.1734)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View =  inflater.inflate(R.layout.fragment_full_map, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val button1m = view.findViewById<Button>(R.id.button1m)
        val button10m = view.findViewById<Button>(R.id.button10m)
        val button100m = view.findViewById<Button>(R.id.button100m)


        button1m.setOnClickListener{
            circle1Fun()
        }

        button10m.setOnClickListener{
            circle10Fun()
        }

        button100m.setOnClickListener{
            circle100Fun()
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.addMarker(MarkerOptions().position(barcelona).title("Barcelona"))
        map.moveCamera(CameraUpdateFactory.newLatLng(barcelona))

        circle1 = map.addCircle(createCircle(1000.0))
        circle10 = map.addCircle(createCircle(10000.0))
        circle100 = map.addCircle(createCircle(100000.0))

        mapReady = true
    }

    fun circle1Fun(){
        if (mapReady){
            if (circle1Activate){
                circle1.setVisible(false)
            } else {
                circle1.setVisible(true)
            }
            circle1Activate = !circle1Activate
        }
    }

    fun circle10Fun(){
        if (mapReady){
            if (circle10Activate){
                circle10.setVisible(false)
            } else {
                circle10.setVisible(true)
            }
            circle10Activate = !circle10Activate
        }
    }

    fun circle100Fun(){
        if (mapReady){
            if (circle100Activate){
                circle100.setVisible(false)
            } else {
                circle100.setVisible(true)
            }
            circle100Activate = !circle100Activate
        }
    }

    fun createCircle(radius: Double): CircleOptions{
        var circleoptions = CircleOptions()
        circleoptions.visible(false)
        circleoptions.center(barcelona)
        circleoptions.radius(radius)
        //circleoptions.fillColor(Color.RED)
        circleoptions.fillColor("44ff0000".toLong(radix = 16).toInt())
        circleoptions.strokeColor(Color.RED)
        circleoptions.strokeWidth(1f)
        return circleoptions
    }

}