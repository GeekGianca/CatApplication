package com.gksoftwaresolutions.catapp.view.ui.votes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gksoftwaresolutions.catapp.R
import com.gksoftwaresolutions.catapp.viewModel.VoteViewModel

class VoteFragment : Fragment() {

    companion object {
        fun newInstance() = VoteFragment()
    }

    private lateinit var viewModel: VoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vote, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}