package com.gksoftwaresolutions.catapp.view.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gksoftwaresolutions.catapp.CatApplication
import com.gksoftwaresolutions.catapp.component.NetworkState
import com.gksoftwaresolutions.catapp.component.adapter.BreedAdapter
import com.gksoftwaresolutions.catapp.databinding.FragmentBreedBinding
import com.gksoftwaresolutions.catapp.model.BreedItem
import com.gksoftwaresolutions.catapp.view.detailBreed.DetailActivity
import com.gksoftwaresolutions.catapp.viewModel.BreedViewModel
import javax.inject.Inject

class BreedFragment : Fragment(), BreedAdapter.IBreedSelectionListener {
    private lateinit var binding: FragmentBreedBinding
    private lateinit var adapterPaged: BreedAdapter

    @Inject
    lateinit var mViewModel: BreedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as CatApplication).catComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedBinding.inflate(inflater)
        binding.refreshList.setOnRefreshListener {
            binding.refreshList.isRefreshing = true
            binding.refreshList.isRefreshing = false
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.breedList().observe(viewLifecycleOwner, {
            adapterPaged = BreedAdapter(null, context, this)
            binding.breedList.apply {
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
    }

    override fun onSelectionClickBreed(item: BreedItem?) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("breed_name", item!!.name)
        requireContext().startActivity(intent)
    }

}