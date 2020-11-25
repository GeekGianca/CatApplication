package com.gksoftwaresolutions.catapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.data.remote.repository.BreedRepository
import com.gksoftwaresolutions.catapp.model.BreedItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BreedViewModel @Inject constructor(private val breedRepoPaged: BreedRepository) :
    ViewModel() {
    private val disposable = CompositeDisposable()

    fun observableNetwork(): LiveData<NetworkState> {
        return breedRepoPaged.observableNetworkState()
    }

    fun breedList(): LiveData<PagedList<BreedItem>> {
        return breedRepoPaged.fetchBreeds(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}