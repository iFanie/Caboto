package com.izikode.izilib.cabotocompiler

import com.izikode.izilib.basekotlincompiler.BaseKotlinCompiler
import com.izikode.izilib.basekotlincompiler.component.CompilationRound
import com.izikode.izilib.basekotlincompiler.source.type.AnnotatedClassSource
import com.izikode.izilib.cabotocomponents.CourseToken
import com.izikode.izilib.cabotocomponents.LandHo
import kotlin.reflect.KClass

class CabotoCompiler : BaseKotlinCompiler() {

    override val processes: Array<KClass<out Any>> = arrayOf(

            LandHo::class,
            CourseToken::class

    )

    private val courseBuilders = hashMapOf<String, CourseBuilder>()
    private val rootCourses = arrayListOf<CourseBuilder>()

    override fun handle(compilationRound: CompilationRound) {
        compilationRound.classesWith(LandHo::class).forEach { activity ->

            var packageExtension = ""
            var addTokens: CourseBuilder? = null

            activity.annotation.course.split("/").forEach { course ->
                addTokens = getBuilder(packageExtension, course)
                packageExtension += if (packageExtension.isEmpty()) course else ".$course"
            }

            addTokens?.let {
                it.tokens = getTokens(activity)
                it.land = it.tokens?.let { activity.info.name }
            }

        }
    }

    private fun getBuilder(packageExtension: String, course: String): CourseBuilder {
        val key = packageExtension + if (packageExtension.isEmpty()) course else ".$course"
        val builder = courseBuilders[key] ?: CourseBuilder(packageExtension, course).also {
                courseBuilders[key] = it

                if (packageExtension.isEmpty()) {
                    rootCourses.add(it)
                }
            }

        if (packageExtension.isNotEmpty()) {
            courseBuilders[packageExtension]?.append(builder)
        }

        return builder
    }

    private fun getTokens(activity: AnnotatedClassSource<LandHo>): ArrayList<CourseBuilder.Token>? {
        val tokens = arrayListOf<CourseBuilder.Token>()

        activity.variablesWith(CourseToken::class).forEach { courseToken ->
            tokens.add(CourseBuilder.Token(

                    /* Name */
                    courseToken.info.name,

                    /* Type */
                    courseToken.info.type,

                    /* Alias */
                    if (courseToken.annotation.alias.isNotEmpty()) courseToken.annotation.alias
                    else courseToken.info.name
            ))
        }

        return if (tokens.isNotEmpty()) tokens else null
    }

    override fun finally() {
        courseBuilders.forEach { key, builder ->
            compilationUtilities.classGenerator.generate(builder)
        }

        if (rootCourses.isNotEmpty()) {
            compilationUtilities.classGenerator.generate(CabotoBuilder(rootCourses))
        }
    }

}
