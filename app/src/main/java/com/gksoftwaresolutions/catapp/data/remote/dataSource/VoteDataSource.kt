package com.gksoftwaresolutions.catapp.data.remote.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gksoftwaresolutions.catapp.data.SustainableService
import com.gksoftwaresolutions.catapp.data.remote.CatClient
import com.gksoftwaresolutions.catapp.model.MakeVote
import com.gksoftwaresolutions.catapp.model.ResultVote
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VoteDataSource {

    companion object {
        private const val TAG = "VoteDataSource"
    }

    private val disposable = CompositeDisposable()
    private val client = SustainableService.createService(CatClient::class.java)
    private val observerResultVote = MutableLiveData<ResultVote>()
    private val observerErrorVote = MutableLiveData<String>()

    fun observableResult(): LiveData<ResultVote> {
        return observerResultVote
    }

    fun observableError(): LiveData<String> {
        return observerErrorVote
    }

    fun makeVote(vote: MakeVote) {
        disposable.add(
            client.createVote(vote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    observerResultVote.value = it
                }, {
                    Log.d(TAG, it.message, it)
                    observerErrorVote.value = "Failed to make vote, try again."
                })
        )
    }

}