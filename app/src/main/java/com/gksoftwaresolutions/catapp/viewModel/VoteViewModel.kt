package com.gksoftwaresolutions.catapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gksoftwaresolutions.catapp.data.local.dataSource.VoteDataSource
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import kotlinx.coroutines.launch
import javax.inject.Inject

class VoteViewModel @Inject constructor(private val dsVote: VoteDataSource) : ViewModel() {

    val observableListVotes: LiveData<List<Vote>> = dsVote.observableVote()

    val observableError: LiveData<String> = dsVote.observableError()

    val observableSuccess: LiveData<String> = dsVote.observableSuccess()

    fun insertVote(vote: Vote) {
        dsVote.saveVote(vote)
    }

    fun allVotes() {
        dsVote.allVotes()
    }
}