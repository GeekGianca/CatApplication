package com.gksoftwaresolutions.catapp.data.remote.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.model.ImageItem
import io.reactivex.disposables.CompositeDisposable

class ImageDataSourceFactory(
    private val client: CatClient,
    private val disposable: CompositeDisposable
) : DataSource.Factory<Int, ImageItem>() {

    val clientImageDataSource = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, ImageItem> {
        val dataSourceClient = ImageDataSource(client, disposable)
        clientImageDataSource.postValue(dataSourceClient)
        return dataSourceClient
    }
}