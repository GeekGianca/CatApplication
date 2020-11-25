package com.gksoftwaresolutions.catapp.view.votes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gksoftwaresolutions.catapp.CatApplication
import com.gksoftwaresolutions.catapp.component.adapter.VoteAdapter
import com.gksoftwaresolutions.catapp.databinding.FragmentVoteBinding
import com.gksoftwaresolutions.catapp.viewModel.VoteViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class VoteFragment : Fragment() {

    private lateinit var binding: FragmentVoteBinding
    private lateinit var adapterVote: VoteAdapter

    @Inject
    lateinit var mViewModel: VoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as CatApplication).catComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoteBinding.inflate(inflater)
        binding.refreshList.setOnRefreshListener {
            binding.refreshList.isRefreshing = true
            mViewModel.allVotes()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.observableListVotes.observe(viewLifecycleOwner, {
            adapterVote = VoteAdapter(it, requireContext())
            binding.voteList.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = adapterVote
                setHasFixedSize(true)
            }
            adapterVote.notifyDataSetChanged()
            if (it.isEmpty()) {
                binding.emptyContent.visibility = View.VISIBLE
            } else {
                binding.emptyContent.visibility = View.GONE
            }
            binding.refreshList.isRefreshing = false
        })

        mViewModel.observableError.observe(viewLifecycleOwner, {
            binding.emptyContent.visibility = View.VISIBLE
            Snackbar.make(binding.content, it, Snackbar.LENGTH_LONG).show()
        })

    }

    override fun onStart() {
        super.onStart()
        mViewModel.allVotes()
    }
}