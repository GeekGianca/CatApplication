package com.gksoftwaresolutions.catapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gksoftwaresolutions.catapp.data.remote.repository.BreedRepository
import com.gksoftwaresolutions.catapp.model.BreedItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val breedRepo: BreedRepository) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun observableErrorFind(): LiveData<String> {
        return breedRepo.breedError()
    }

    fun observableBreedItem(): LiveData<BreedItem> {
        return breedRepo.observableBreed();
    }

    fun findBreed(breedName: String) {
        breedRepo.fetchBreed(disposable, breedName)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}