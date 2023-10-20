package com.example.citycards.Model

class QueryDataCity(
    val limiteur: Int = 10,
    val namePrefix: String = "",
    val offset: Int = 0,
    val minPop: Int = 15000,
    val maxPop: Int? = null
)
