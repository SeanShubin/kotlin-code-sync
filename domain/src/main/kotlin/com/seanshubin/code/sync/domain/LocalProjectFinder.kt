package com.seanshubin.code.sync.domain

interface LocalProjectFinder {
    fun findAll(): List<String>
}