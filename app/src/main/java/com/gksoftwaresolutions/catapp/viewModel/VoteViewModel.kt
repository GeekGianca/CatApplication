package com.gksoftwaresolutions.catapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gksoftwaresolutions.catapp.data.local.dataSource.VoteLocalDataSource
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import javax.inject.Inject

class VoteViewModel @Inject constructor(private val dsVoteLocal: VoteLocalDataSource) : ViewModel() {

    val observableListVotes: LiveData<List<Vote>> = dsVoteLocal.observableVote()

    val observableError: LiveData<String> = dsVoteLocal.observableError()

    val observableSuccess: LiveData<String> = dsVoteLocal.observableSuccess()

    fun allVotes() {
        dsVoteLocal.allVotes()
    }
}