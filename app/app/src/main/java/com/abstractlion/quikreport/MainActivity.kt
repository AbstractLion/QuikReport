package com.abstractlion.quikreport

import android.os.Bundle
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.channel.SubscriptionEventListener


class MainActivity : AppCompatActivity() {

    private var markerX: Int = 0
    private var markerY: Int = 0
    private var marker: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        marker = findViewById(R.id.marker1)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val options = PusherOptions()
        options.setCluster("us2")
        val pusher = Pusher("adf7ac7a5c51dcc45eb4", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                println("State changed from ${change.previousState} to ${change.currentState}")
            }

            override fun onError(message: String, code: String, e: java.lang.Exception) {
                println("There was a problem connecting!\nCode:$code\nMessage:$message\nException:$e")
            }
        }, ConnectionState.ALL)


        val channel: Channel = pusher.subscribe("my-channel")

        var eventListener : SubscriptionEventListener = new SubscriptionEventListener() {
            override public void onEvent(String channel, final String event, final String data) {
                runOnUiThread(new Runnable() {
                    override public void run() {
                        println("Received event with data: " + data);
                        Gson gson = new Gson();
                        Event evt = gson.fromJson(data, Event.class);
                        evt.setName(event + ":");
                        adapter.addEvent(evt);
                        ((LinearLayoutManager)lManager).scrollToPositionWithOffset(0, 0);
                    }
                });
            }
        }

        channel.bind("my-event") { event -> println("Received event with data: $event") }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (marker == null) {
            println("Marker is null."); return true
        }
        var layoutParams : FrameLayout.LayoutParams = marker?.layoutParams as FrameLayout.LayoutParams
        markerX = event.x.toInt() - 20
        markerY = event.y.toInt() - 390
        layoutParams.leftMargin = markerX;
        layoutParams.topMargin = markerY;
        marker?.setLayoutParams(layoutParams);
        marker?.bringToFront()
        return true
    }

    fun onClick(view: View) {
        view.requestPointerCapture()
    }
}
