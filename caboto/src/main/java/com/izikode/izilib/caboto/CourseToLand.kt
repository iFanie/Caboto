package com.izikode.izilib.caboto

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

data class CourseToLand<Land>(

        private val navigationIntent: Intent,
        private val navigationEnd: KClass<Land>

) where Land : Activity {

    fun setSail(startingPoint: Activity) {
        startingPoint.startActivity(
                Intent(

                        startingPoint,
                        navigationEnd.java

                ).apply {

                    navigationIntent.extras?.let {
                        putExtras(it)
                    }

                }
        )
    }

    fun setSail(startingPoint: Context) {
        setSail(startingPoint as android.app.Activity)
    }

}