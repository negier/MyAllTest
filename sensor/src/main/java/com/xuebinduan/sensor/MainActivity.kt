package com.xuebinduan.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var sensorManager:SensorManager
    private lateinit var view:View
    private lateinit var sensor:Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById<View>(R.id.view)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            val values = event.values
            val x = values[0] * 50f
            val y = -values[1] * 50f
            val z = values[2] * 50f
            Log.e("TAG","x:$x,y:$y,z:$z")
            view.translationX = x
            view.translationY = y
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}