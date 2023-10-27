package com.example.citycards.Model

import android.graphics.Region

class QueryDataCity(
    var limiteur: Int = 10,
    var namePrefix: String = "",
    var offset: Int = 0,
    var minPop: Int = 15000,
    var maxPop: Int? = null,
    var region: String? = null,
    var rang: Int? = null
)
