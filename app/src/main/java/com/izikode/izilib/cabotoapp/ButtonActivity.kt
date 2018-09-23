package com.izikode.izilib.cabotoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.izikode.izilib.cabotocomponents.CourseToken
import com.izikode.izilib.cabotocomponents.LandHo
import java.io.Serializable

@LandHo(course = "widget/button")
class ButtonActivity : AppCompatActivity() {

    @CourseToken
    lateinit var title: String

    @CourseToken
    lateinit var buttonText: String

    @CourseToken
    var counter: Int = 0

    /* TYPES */

    @CourseToken
    var int: Int? = null

    @CourseToken
    var byte: Byte? = null

    @CourseToken
    var char: Char? = null

    @CourseToken
    var charSequence: CharSequence? = null

    @CourseToken
    var long: Long? = null

    @CourseToken
    var float: Float? = null

    @CourseToken
    var short: Short? = null

    @CourseToken
    var double: Double? = null

    @CourseToken
    var boolean: Boolean? = null

    @CourseToken
    var string: String? = null

    @CourseToken
    var bundle: Bundle? = null

    @CourseToken
    var serializable: Serializable? = null

}