package com.lmy.annotation

/**
 * 指定Activity的PageId
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PageId(val value: String)
