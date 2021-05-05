package com.miguelzaragozaserrano.dam.v2.data.models

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Polyline

data class MapViewState(
    var urlPolyline: String? = null,
    var mapType: Int = GoogleMap.MAP_TYPE_NORMAL,
    var polyline: Polyline? = null,
    var locationEnable: Boolean = false
)