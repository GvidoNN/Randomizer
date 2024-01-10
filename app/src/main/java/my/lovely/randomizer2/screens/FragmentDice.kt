package my.lovely.randomizer2.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import my.lovely.randomizer2.R
import java.lang.Math.sqrt
import java.util.Objects

class FragmentDice : Fragment(R.layout.fragment_dice) {

    private lateinit var imDice1: ImageView
    private lateinit var imDice2: ImageView
    private lateinit var imDice3: ImageView

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    private var currentOrientationX = 0f
    private var currentOrientationY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)?.registerListener(sensorListener, sensorManager!!
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            currentOrientationX = x / SensorManager.GRAVITY_EARTH
            currentOrientationY = y / SensorManager.GRAVITY_EARTH

            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 12) {
                rollDice(randomNumber(), imDice1)
                rollDice(randomNumber(), imDice2)
                rollDice(randomNumber(), imDice3)
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView()
        onDiceTap()
    }

    private fun findView() {
        imDice1 = requireView().findViewById(R.id.imDice1)
        imDice2 = requireView().findViewById(R.id.imDice2)
        imDice3 = requireView().findViewById(R.id.imDice3)
    }

    private fun onDiceTap() {
        imDice1.setOnClickListener {
            imDice1.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice1)
        }
        imDice2.setOnClickListener {
            imDice2.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice2)
        }
        imDice3.setOnClickListener {
            imDice3.setImageResource(R.drawable.ic_cube)
            rollDice(randomNumber(), imDice3)
        }
    }

    private fun rollDice(imageId: Int, imDice: ImageView) {
        imDice.animate().apply {
            duration = 1000
            rotationYBy(1800f)
            rotationXBy(1800f)
            imDice.isClickable = false
        }.withEndAction {
            imDice.setImageResource(imageId)
            imDice.isClickable = true

            imDice.animate().apply {
                duration = 200
                rotationX(-currentOrientationX)
                rotationY(-currentOrientationX)
            }.start()

        }.start()
    }

    private fun randomNumber(): Int {
        val number = (1..6).random()
        return when (number) {
            1 -> R.drawable.ic_dice1
            2 -> R.drawable.ic_dice2
            3 -> R.drawable.ic_dice3
            4 -> R.drawable.ic_dice4
            5 -> R.drawable.ic_dice5
            6 -> R.drawable.ic_dice6
            else -> R.drawable.ic_cube
        }
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }
}