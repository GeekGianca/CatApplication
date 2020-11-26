package com.gksoftwaresolutions.catapp.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.data.SustainableService
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.data.remote.dataSource.ImageDataSource
import com.gksoftwaresolutions.catapp.data.remote.dataSource.ImageDataSourceFactory
import com.gksoftwaresolutions.catapp.model.ImageItem
import com.gksoftwaresolutions.catapp.util.Common
import io.reactivex.disposables.CompositeDisposable

class ImageRepository {
    companion object {
        private const val TAG = "ImageRepository"
    }

    private lateinit var client: CatClient
    lateinit var breedList: LiveData<PagedList<ImageItem>>
    private lateinit var clientDataSourceFactory: ImageDataSourceFactory

    fun fetchImages(disposable: CompositeDisposable): LiveData<PagedList<ImageItem>> {
        client = SustainableService.createService(CatClient::class.java)
        clientDataSourceFactory = ImageDataSourceFactory(client, disposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Common.POST_PER_PAGE)
            .build()
        breedList = LivePagedListBuilder(clientDataSourceFactory, config).build()
        return breedList
    }

    fun observableNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            clientDataSourceFactory.clientImageDataSource,
            ImageDataSource::networkState
        )
    }
}