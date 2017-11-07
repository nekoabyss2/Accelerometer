package com.egci428.accelerometer

import android.content.Context
import android.hardware.SensorManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),SensorEventListener {

    private var sensorManeger: SensorManager? = null
    private var color = false
    private var view: View? = null
    private var lastUpdate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById(R.id.textView)
        view!!.setBackgroundColor(Color.GREEN)
        textView.setBackgroundColor(Color.BLUE)

        sensorManeger = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lastUpdate = System.currentTimeMillis()
    }

    override fun onSensorChanged(event: SensorEvent){

        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            val values = event!!.values
            val x = values[0]
            val y = values[1]
            val z = values[2]

            val accel = (x * x + y * y + z * z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)
            val actualTime = System.currentTimeMillis()

            if (accel >= 2){
                if (actualTime - lastUpdate < 200){
                    return // do nothing
                }

                lastUpdate = actualTime
                Toast.makeText(this,"Device was shuffled", Toast.LENGTH_SHORT)

                if(color){
                    textView.setBackgroundColor(Color.YELLOW)
                } else{
                    textView.setBackgroundColor(Color.BLUE)
                }
                color = !color
            }

        }
    }

    private fun getAccelerometer(event: SensorEvent){

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int){
        TODO("not implemented") //to change body of created functions use File | Settings| File templates
    }

    override fun onResume(){
        super.onResume()
        sensorManeger!!.registerListener(this, sensorManeger!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManeger!!.unregisterListener(this)
    }
}
