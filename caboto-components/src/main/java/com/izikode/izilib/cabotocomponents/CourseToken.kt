package com.izikode.izilib.cabotocomponents

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class CourseToken(

        /**
         * A optional alias for the token.
         */
        val alias: String = ""

)