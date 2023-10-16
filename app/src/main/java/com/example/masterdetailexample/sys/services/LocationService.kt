package com.example.masterdetailexample.sys.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.masterdetailexample.R
import android.location.Location
import android.util.Log
import com.example.masterdetailexample.common.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask

const val TAG = "LocationService"
class LocationService : Service() {

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var altitud: Double = 0.0
    var speed: Double = 0.0
    var course: Double = 0.0
    var accuracy : Double = 0.0
    var batLevel : Int = 0
    var manufacturer : String = ""
    var model : String = ""
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    override fun onCreate() {
        super.onCreate()
        val bm: BatteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        manufacturer = Build.MANUFACTURER
        model = Build.MODEL

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            createNotificationChanel()
        else
            startForeground(1, Notification())
        requestLocationUpdates()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                if (latitude != 0.0 && longitude != 0.0) {
                    try {
                        val time = Calendar.getInstance().time
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                        val current = formatter.format(time)
                        val data = hashMapOf(
                            "lat" to latitude.toString(),
                            "long" to longitude.toString(),
                            "timeLocation" to current
                        )
                        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
                        db.collection(Constants.COLLECTION_FIRESTORE)
                            .add(data)
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    } catch (e: Exception) {

                    }
                }
            }
        }
        timer!!.schedule(
            timerTask,
            0,
            10000
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val NOTIFICATION_CHANNEL_ID = "com.ruta99"
        val channelName = "Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.RED
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager =
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle(getString(R.string.text_title_notification))
            .setContentText(getString(R.string.text_notification))
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
        startForeground(2, notification)
    }

    private fun requestLocationUpdates() {
        val request = LocationRequest()
        request.interval = 10000
        request.fastestInterval = 5000
        request.priority = LocationRequest.PRIORITY_LOW_POWER
        val client: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        altitud = location.altitude
                        speed = location.speed.toDouble()
                        course = location.bearing.toDouble()
                        accuracy = location.accuracy.toDouble()
                    }
                }
            }, null)
        }
    }
}