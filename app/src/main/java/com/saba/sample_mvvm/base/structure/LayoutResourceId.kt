package com.saba.sample_mvvm.base.structure

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutResourceId(
    val value: Int
)