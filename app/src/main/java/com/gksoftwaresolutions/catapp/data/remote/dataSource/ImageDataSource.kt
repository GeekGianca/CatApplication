package com.gksoftwaresolutions.catapp.data.remote.dataSource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.model.ImageItem
import com.gksoftwaresolutions.catapp.util.Common
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageDataSource(
    private val client: CatClient,
    private val disposable: CompositeDisposable
) : PageKeyedDataSource<Int, ImageItem>() {

    companion object {
        private const val TAG = "ImageDataSource"
    }

    private var page = 1
    private var start = 0

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageItem>
    ) {
        networkState.postValue(NetworkState.LOADING)
        Common.PARAMS["page"] = "$page"
        Common.PARAMS["limit"] = "${Common.POST_PER_PAGE}"
        Common.PARAMS["size"] = Common.SIZE_IMAGE
        disposable.add(
            client.imagesList(Common.PARAMS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it, null, page + 1)
                    if (it.isEmpty()) {
                        networkState.postValue(NetworkState.EMPTY)
                    } else {
                        networkState.postValue(NetworkState.LOADED)
                    }
                }, {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e(TAG, it.message, it)
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        networkState.postValue(NetworkState.LOADING)
        page = params.key
        start += Common.POST_PER_PAGE
        Common.PARAMS["page"] = "$page"
        Common.PARAMS["limit"] = "${Common.POST_PER_PAGE}"
        Common.PARAMS["size"] = Common.SIZE_IMAGE
        disposable.add(
            client.imagesList(Common.PARAMS)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.size >= params.key) {
                        callback.onResult(it, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.END_OF_LIST)
                    }
                }, {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e(TAG, it.message, it)
                })
        )
    }
}