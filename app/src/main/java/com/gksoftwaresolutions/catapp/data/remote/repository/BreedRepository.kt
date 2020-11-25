package com.gksoftwaresolutions.catapp.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BreedRepository {
    companion object {
        private const val TAG = "BreedRepository"
    }

    private lateinit var client: CatClient
    lateinit var breedList: LiveData<PagedList<BreedItem>>
    private lateinit var clientDataSourceFactory: BreedDataSourceFactory
    private val error = MutableLiveData<String>()

    private val breedMutable = MutableLiveData<BreedItem>()

    fun observableBreed(): LiveData<BreedItem> {
        return breedMutable
    }

    fun breedError(): LiveData<String> {
        return error
    }

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

    fun fetchBreed(disposable: CompositeDisposable, breed: String): LiveData<BreedItem> {
        disposable.add(
            client.searchBreed(breed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    breedMutable.value = it
                }, {
                    Log.d(TAG, it.message, it)
                    error.value = it.message
                })
        )
        return breedMutable
    }

    fun observableNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            clientDataSourceFactory.clientDataSource,
            BreedDataSource::networkState
        )
    }
}