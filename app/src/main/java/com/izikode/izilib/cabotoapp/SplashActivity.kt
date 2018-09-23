package com.izikode.izilib.cabotoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.izikode.izilib.caboto.Caboto
import com.izikode.izilib.cabotocomponents.LandHo

@LandHo(course = "initial/splash")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Caboto.NAVIGATOR.initial.main.prepare("Caboto Sample Application").setSail(this)
    }

}