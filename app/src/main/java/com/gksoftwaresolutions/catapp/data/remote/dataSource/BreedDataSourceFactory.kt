package com.gksoftwaresolutions.catapp.data.remote.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.model.BreedItem
import io.reactivex.disposables.CompositeDisposable


class BreedDataSourceFactory(
    private val client: CatClient,
    private val disposable: CompositeDisposable
) : DataSource.Factory<Int, BreedItem>() {

    val clientDataSource = MutableLiveData<BreedDataSource>()

    override fun create(): DataSource<Int, BreedItem> {
        val dataSourceClient = BreedDataSource(client, disposable)
        clientDataSource.postValue(dataSourceClient)
        return dataSourceClient
    }
}