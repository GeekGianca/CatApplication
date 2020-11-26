package com.gksoftwaresolutions.catapp.view.home

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gksoftwaresolutions.catapp.CatApplication
import com.gksoftwaresolutions.catapp.R
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.component.adapter.ImageAdapter
import com.gksoftwaresolutions.catapp.data.local.entity.Vote
import com.gksoftwaresolutions.catapp.databinding.FragmentHomeBinding
import com.gksoftwaresolutions.catapp.model.ImageItem
import com.gksoftwaresolutions.catapp.model.MakeVote
import com.gksoftwaresolutions.catapp.util.Common
import com.gksoftwaresolutions.catapp.viewModel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment(), ImageAdapter.IMakeVoteListener {

    //Define Layout with ViewBinding, reduce boilerplate
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterPaged: ImageAdapter
    private lateinit var vote: Vote
    private lateinit var progress: ProgressDialog

    @Inject
    lateinit var mViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as CatApplication).catComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.breedList().observe(viewLifecycleOwner, {
            adapterPaged = ImageAdapter(null, context, this)
            binding.imageList.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = adapterPaged
                setHasFixedSize(true)
            }
            adapterPaged.submitList(it)
            adapterPaged.notifyDataSetChanged()
        })

        mViewModel.observableNetwork().observe(viewLifecycleOwner, { networkState ->
            when {
                networkState === NetworkState.EMPTY -> {
                    binding.loading.visibility = View.GONE
                    binding.emptyContent.visibility = View.VISIBLE
                }
                networkState === NetworkState.END_OF_LIST -> {
                    binding.loading.visibility = View.GONE
                    binding.emptyContent.visibility = View.GONE
                }
                networkState === NetworkState.LOADED -> {
                    binding.loading.visibility = View.GONE
                    binding.emptyContent.visibility = View.GONE
                }
                networkState === NetworkState.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.emptyContent.visibility = View.GONE
                }
                networkState === NetworkState.ERROR -> {
                    binding.loading.visibility = View.GONE
                    binding.emptyContent.visibility = View.VISIBLE
                }
            }
        })

        mViewModel.observableErrorVote().observe(viewLifecycleOwner, {
            progress.dismiss()
            Snackbar.make(binding.content, it, Snackbar.LENGTH_LONG).show()
        })

        mViewModel.observableResultVote().observe(viewLifecycleOwner, {
            progress.dismiss()
            mViewModel.makeVote(vote)
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle("Voted successfully")
                .setMessage("Thank you for your vote to this beautiful feline.\n" +
                        "Check the voting history in the voting section")
                .setPositiveButton("Accept") { dialog, _ ->
                    dialog.dismiss()
                }.create()
                .show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.refreshList.setOnRefreshListener {
            binding.refreshList.isRefreshing = true
            binding.refreshList.isRefreshing = false
        }
        return binding.root
    }

    override fun onCreateVote(item: ImageItem?, vote: Int) {
        this.vote = Vote(0, item!!.id, Common.SDF.format(Date()), item.url, vote)
        mViewModel.sendVoteRemote(MakeVote(item.id, sub_id = "my-user-1234", vote))
        progress = ProgressDialog(requireContext(), R.style.AppCompatAlertDialogStyle)
        progress.setMessage("Waiting...")
        progress.show()
    }
}