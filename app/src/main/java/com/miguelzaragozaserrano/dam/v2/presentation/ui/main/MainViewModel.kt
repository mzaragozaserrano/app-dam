package com.miguelzaragozaserrano.dam.v2.presentation.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.miguelzaragozaserrano.dam.v2.domain.models.*
import com.miguelzaragozaserrano.dam.v2.data.db.CameraDatabase
import com.miguelzaragozaserrano.dam.v2.data.db.entity.CameraEntity
import com.miguelzaragozaserrano.dam.v2.domain.repositories.CameraRepository
import com.miguelzaragozaserrano.dam.v2.presentation.ui.base.BaseViewModel
import com.miguelzaragozaserrano.dam.v2.presentation.utils.UtilsDownload.downloadFile
import com.miguelzaragozaserrano.dam.v2.presentation.utils.UtilsDownload.numberCameras
import com.miguelzaragozaserrano.dam.v2.presentation.utils.UtilsDownload.onCameraDownload
import com.miguelzaragozaserrano.dam.v2.presentation.utils.toCameraEntity
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : BaseViewModel() {

    private val database by lazy { CameraDatabase.getInstance(context) }
    private val repository by lazy { CameraRepository(database.cameraDao) }

    var isFirstTime = true
    var isRechargeRequest = false
    var allCameras = mutableListOf<Camera>()

    var mapViewState = MapViewState()
    var adapterState = AdapterState()
    var searchViewState = SearchViewState()
    var settingsMapState = SettingsMapState(cameras = allCameras)

    var dbListCameras: LiveData<List<CameraEntity>> = repository.getAllCameras()

    fun isFileDownloaded(): Boolean =
        dbListCameras.value?.size == (numberCameras?.minus(1))

    fun clearDatabase() = viewModelScope.launch {
        repository.clearDatabase()
    }

    fun getDataFromUrl() {
        downloadFile()
        onCameraDownload = { camera ->
            insert(camera = camera)
        }
    }

    fun setCameraFavorite(camera: Camera, favorite: Boolean) = viewModelScope.launch {
        repository.updateFavorite(camera.id, favorite)
    }

    private fun insert(camera: Camera) = viewModelScope.launch {
        repository.insert(camera = camera.toCameraEntity())
    }

}