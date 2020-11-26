package com.gksoftwaresolutions.catapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.data.local.dataSource.VoteLocalDataSource
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import com.gksoftwaresolutions.catapp.data.remote.dataSource.VoteDataSource
import com.gksoftwaresolutions.catapp.data.remote.repository.ImageRepository
import com.gksoftwaresolutions.catapp.model.ImageItem
import com.gksoftwaresolutions.catapp.model.MakeVote
import com.gksoftwaresolutions.catapp.model.ResultVote
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val imageRepo: ImageRepository,
    private val voteLocalDs: VoteLocalDataSource,
    private val voteRemoteDs: VoteDataSource
) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun observableNetwork(): LiveData<NetworkState> {
        return imageRepo.observableNetworkState()
    }

    fun breedList(): LiveData<PagedList<ImageItem>> {
        return imageRepo.fetchImages(disposable)
    }

    fun observableSuccessVoteLocal(): LiveData<String> {
        return voteLocalDs.observableSuccess();
    }

    fun observableResultVote(): LiveData<ResultVote> {
        return voteRemoteDs.observableResult()
    }

    fun observableErrorVote(): LiveData<String> {
        return voteRemoteDs.observableError()
    }

    fun sendVoteRemote(vote: MakeVote) {
        voteRemoteDs.makeVote(vote)
    }

    fun makeVote(vote: Vote) {
        voteLocalDs.saveVote(vote)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}