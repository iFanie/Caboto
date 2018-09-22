package com.izikode.izilib.cabotocompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass
import java.lang.StringBuilder

class CabotoBuilder(private val rootCourses: ArrayList<out CourseBuilder>) : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.caboto"

    override val simpleName: String = "Caboto"

    private val rootCourseProperties: String get() =
        StringBuilder().apply {
            rootCourses.forEachIndexed { index, course ->
                append("val ${course.name}: ${course.canonicalName}\n\t\t\tby lazy { ${course.canonicalName}() }")

                if (index <= rootCourses.size - 2) {
                    append("\n\n\t")
                }
            }
        }.toString()

    private val rootCourseNames: String get() =
        StringBuilder().apply {
            rootCourses.forEachIndexed { index, course ->
                append("[${course.name}]")

                if (index <= rootCourses.size - 2) {
                    append(", ")
                }
            }
        }.toString()

    override val sourceCode: String =
"""package $packageName

/**
 * Caboto navigator singleton class.
 * The starting point for all the navigational routes built with Caboto.
 * Immediate routes are $rootCourseNames
 */
class $simpleName private constructor() {

    $rootCourseProperties

    companion object {

        @JvmStatic
        val NAVIGATOR: $simpleName by lazy { $simpleName() }

    }

}
"""
}
