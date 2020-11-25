package com.gksoftwaresolutions.catapp.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.data.SustainableService
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.data.remote.dataSource.BreedDataSource
import com.gksoftwaresolutions.catapp.data.remote.dataSource.BreedDataSourceFactory
import com.gksoftwaresolutions.catapp.model.BreedItem
import com.gksoftwaresolutions.catapp.util.Common
import io.reactivex.disposables.CompositeDisposable

class BreedRepository() {
    companion object {
        private const val TAG = "BreedRepository"
    }

    private lateinit var client: CatClient
    lateinit var breedList: LiveData<PagedList<BreedItem>>
    private lateinit var clientDataSourceFactory: BreedDataSourceFactory

    fun fetchBreeds(disposable: CompositeDisposable): LiveData<PagedList<BreedItem>> {
        client = SustainableService.createService(CatClient::class.java)
        clientDataSourceFactory = BreedDataSourceFactory(client, disposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Common.POST_PER_PAGE)
            .build()
        breedList = LivePagedListBuilder(clientDataSourceFactory, config).build()
        return breedList
    }

    fun observableNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            clientDataSourceFactory.clientDataSource,
            BreedDataSource::networkState
        )
    }
}