package com.gksoftwaresolutions.catapp.component

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gksoftwaresolutions.catapp.view.MainActivity
import com.gksoftwaresolutions.catapp.view.ui.categories.BreedFragment
import com.gksoftwaresolutions.catapp.view.ui.categories.BreedViewModel
import com.gksoftwaresolutions.catapp.view.ui.home.HomeViewModel
import com.gksoftwaresolutions.catapp.view.ui.votes.VoteViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Provides
    fun getContext(): Context = context

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BreedViewModel::class.java) -> {
                BreedViewModel() as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel() as T
            }
            modelClass.isAssignableFrom(VoteViewModel::class.java) -> {
                VoteViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}