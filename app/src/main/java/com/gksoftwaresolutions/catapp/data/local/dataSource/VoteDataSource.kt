package com.gksoftwaresolutions.catapp.data.local.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gksoftwaresolutions.catapp.data.local.dao.VoteDao
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoteDataSource @Inject constructor(private val voteDao: VoteDao) {
    companion object {
        private const val TAG = "VoteDataSource"
    }

    private val disposable = CompositeDisposable()
    private val observerVote = MutableLiveData<List<Vote>>()
    private val observerError = MutableLiveData<String>()
    private val observerSuccess = MutableLiveData<String>()

    fun observableVote(): LiveData<List<Vote>> {
        return observerVote
    }

    fun observableError(): LiveData<String> {
        return observerError
    }

    fun observableSuccess(): LiveData<String> {
        return observerSuccess
    }

    fun allVotes() {
        disposable.add(
            voteDao.selectAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    observerVote.value = it
                }, {
                    Log.e(TAG, it.message, it)
                    observerError.value = it.message
                })
        )
    }

    fun saveVote(vote: Vote) {
        disposable.add(
            Observable.create { _: ObservableEmitter<Any?>? ->
                voteDao.insert(vote)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Subscribe Object to Insert Note")
                }, { throwable: Throwable ->
                    Log.e(TAG, throwable.message, throwable)
                    observerError.value = "Failed to save Vote: ${throwable.message}"
                }) {
                    observerSuccess.value = "Vote register Successfully"
                })
    }

}