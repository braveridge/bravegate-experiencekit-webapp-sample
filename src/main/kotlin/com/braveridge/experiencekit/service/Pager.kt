package com.braveridge.experiencekit.service

import kotlin.math.ceil

open class Pager(
    val totalCount: Long = 0,
    val currentPage: Int = 1,
    val countPerPage: Int = 10
) {
    fun hasPrev() = currentPage > 1
    open fun hasNext() = totalCount > currentPage * countPerPage
    open fun lastPage() = ceil(totalCount.toFloat() / countPerPage.toFloat()).toInt()
    fun prevPage() = if (hasPrev()) currentPage - 1 else 1
    fun nextPage() = if (hasNext()) currentPage + 1 else lastPage()

    companion object {
        fun all(totalCount: Long) = Pager(totalCount, 1, totalCount.toInt())
    }
}

class SimplePager(
    currentPage: Int = 1,
    countPerPage: Int = 10
) : Pager(0, currentPage, countPerPage) {
    override fun hasNext() = true
    override fun lastPage() = currentPage + 1
}