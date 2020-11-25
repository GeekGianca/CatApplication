package com.gksoftwaresolutions.catapp.view.ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gksoftwaresolutions.catapp.R

class BreedFragment : Fragment() {

    companion object {
        fun newInstance() = BreedFragment()
    }

    private lateinit var viewModel: BreedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}