package com.abstractlion.quikreport

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

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

        channel.bind("my-event") { event -> println("Received event with data: $event") }
        /*
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas : Canvas = Canvas(bitmap)
        var dot : ShapeDrawable = ShapeDrawable(OvalShape())
        dot.setBounds(123,34,4335,334)
        dot.draw(canvas)
        imageV.background = BitmapDrawable(getResources(), bitmap)

         */
    }
}
