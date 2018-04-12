package com.example.aaron.talkerm

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

/**
 * Created by gdinh on 3/31/2018.
 */
class ControlActivity : AppCompatActivity(){


    private var shake: Button? = null
    private var beep: Button? = null
    private var ivOnOff: Button? = null
    private var isTorchOn: Boolean? = null
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Buttons for flash, beep, shake
        ivOnOff = findViewById(R.id.flash) as Button
        shake = findViewById(R.id.shake) as Button
        beep = findViewById(R.id.beep) as Button
        isTorchOn = false

        //Connects to camera device
        cameraManager= getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try{
            cameraId = cameraManager!!.cameraIdList[0]
        }
        catch(e: CameraAccessException){
            e.printStackTrace()
        }

        // When Button is clicked, it turns on or off the flashlight
        ivOnOff!!.setOnClickListener{
            try{
                if(isTorchOn!!){
                    turnOffLights()
                    isTorchOn = false
                }
                else{
                    turnOnLights()
                    isTorchOn = true
                }
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }


        //Vibrates when button is clicked
        shake!!.setOnClickListener{
            vibrateDevice(it)
        }

        //Beeps when button is clicked
        beep!!.setOnClickListener{
            makeSound(it)
        }

    }

    //Function to turn lights on when button is clicked
    fun turnOnLights() {
        try {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                cameraManager!!.setTorchMode(cameraId!!, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Function to turn lights off when button is clicked
    fun turnOffLights() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager!!.setTorchMode(cameraId!!, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //Function to vibrate the phone when button is clicked
    fun vibrateDevice(view: View){
        var vibrator : Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(500)
    }

    //Function to beep the phone when button is clicked
    fun makeSound(view: View){
        val tone = ToneGenerator(AudioManager.STREAM_MUSIC,100)
        tone.startTone(ToneGenerator.TONE_DTMF_3,300)
    }
}