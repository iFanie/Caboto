package com.izikode.izilib.cabotocompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass
import java.lang.StringBuilder

class CourseBuilder(packageExtension: String, val name: String) : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.caboto.course" +
            if (packageExtension.isNotEmpty()) ".$packageExtension" else ""

    override val simpleName: String = "${name.capitalize()}Course"

    private val subCourses = arrayListOf<CourseBuilder>()

    fun append(courseBuilder: CourseBuilder) {
        if (!subCourses.contains(courseBuilder)) {
            subCourses.add(courseBuilder)
        }
    }

    private val subCourseProperties: String get() =
        StringBuilder().apply {
            subCourses.forEachIndexed { index, course ->
                append("val ${course.name}: ${course.canonicalName}\n\t\t\tby lazy { ${course.canonicalName}() }")

                if (index <= subCourses.size - 2) {
                    append("\n\n\t")
                }
            }
        }.toString()

    var tokens: ArrayList<Token>? = null

    var land: String? = null

    private val prepareFootprint: String get() = StringBuilder().apply {
        tokens?.let {
            it.forEachIndexed { index, token ->
                append(token.name)
                append(": ")
                append(token.type)

                if (token.type.endsWith("?")) {
                    append(" = null")
                }

                if (index <= it.size - 2) {
                    append(", ")
                }
            }
        }
    }.toString()

    private val prepareExtras: String get() = StringBuilder().apply {
        tokens?.let {
            it.forEachIndexed { index, token ->
                if (token.type.endsWith("?")) {
                    append("${token.name}?.let { putExtra(\"${token.alias}\", it) }")
                } else {
                    append("putExtra(\"${token.alias}\", ${token.name})")
                }

                if (index <= it.size - 2) {
                    append("\n\t\t\t\t\t\t")
                }
            }
        }
    }.toString()

    private val prepareFunction: String get() = tokens?.let {
"""
    @JvmOverloads
    fun prepare($prepareFootprint)
            = com.izikode.izilib.caboto.CourseToLand<${land!!}>(
                android.content.Intent().apply {
                    $prepareExtras
                },

                ${land!!}::class
            )
"""
    } ?: ""

    private val routeType: String get() = tokens?.let { "Landing" } ?: "Passthrough"

    private val routeTypeClass: String get() = "com.izikode.izilib.caboto.${routeType}Course"

    private val subCourseLabel: String get() =
        if (subCourses.isNotEmpty()) "Immediate sub routes are" else "No immediate sub routes."

    private val subCourseNames: String get() =
        StringBuilder().apply {
            subCourses.forEachIndexed { index, course ->
                append("[${course.name}]")

                if (index <= subCourses.size - 2) {
                    append(", ")
                }
            }
        }.toString()

    override val sourceCode: String get() =
"""package $packageName

/**
 * Caboto generated Course class, the ${name.capitalize()} navigational route.
 * Is a $routeType course.
 * $subCourseLabel $subCourseNames
 */
class $simpleName : $routeTypeClass() {

    $prepareFunction

    $subCourseProperties

}
"""

    data class Token(val name: String, val type: String, val alias: String)

}