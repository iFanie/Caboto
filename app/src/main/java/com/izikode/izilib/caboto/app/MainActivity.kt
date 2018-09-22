package com.izikode.izilib.caboto.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.izikode.izilib.caboto.Caboto
import com.izikode.izilib.cabotocomponents.CourseToken
import com.izikode.izilib.cabotocomponents.LandHo
import kotlinx.android.synthetic.main.activity_main.*

@LandHo(course = "initial/main")
class MainActivity : AppCompatActivity() {

    @CourseToken(alias = "MainActivityTitle")
    lateinit var title: String

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.extras?.getString("MainActivityTitle")?.let {
            title = it
        }

        toButtonButton.setOnClickListener {
            counter++
            Caboto.NAVIGATOR.widget.button.prepare(title, "Click Me!", counter).setSail(this)
            Caboto.NAVIGATOR
                    .initial.main                       /* Course */
                    .prepare(title = "Caboto rocks")    /* Initialization variables */
                    .setSail(this)                      /* Go */
        }


    }

}
