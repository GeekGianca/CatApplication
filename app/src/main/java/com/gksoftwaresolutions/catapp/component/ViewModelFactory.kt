package com.gksoftwaresolutions.catapp.component

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gksoftwaresolutions.catapp.data.local.LocalDatabase
import com.gksoftwaresolutions.catapp.data.local.dataSource.VoteLocalDataSource
import com.gksoftwaresolutions.catapp.data.remote.dataSource.VoteDataSource
import com.gksoftwaresolutions.catapp.data.remote.repository.BreedRepository
import com.gksoftwaresolutions.catapp.data.remote.repository.ImageRepository
import com.gksoftwaresolutions.catapp.viewModel.BreedViewModel
import com.gksoftwaresolutions.catapp.viewModel.DetailViewModel
import com.gksoftwaresolutions.catapp.viewModel.HomeViewModel
import com.gksoftwaresolutions.catapp.viewModel.VoteViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactory(private val context: Context, private val local: LocalDatabase) :
    ViewModelProvider.Factory {

    @Provides
    fun getContext(): Context = context

    @Provides
    fun getLocalDatabase(): LocalDatabase = local

    @Provides
    fun getVoteDataSource(): VoteLocalDataSource = VoteLocalDataSource(local.voteDao())

    @Provides
    fun getBreedRepository(): BreedRepository = BreedRepository()

    @Provides
    fun getImageRepository(): ImageRepository = ImageRepository()

    @Provides
    fun getVoteRemoteDataSource(): VoteDataSource = VoteDataSource()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BreedViewModel::class.java) -> {
                BreedViewModel(getBreedRepository()) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    getImageRepository(),
                    getVoteDataSource(),
                    getVoteRemoteDataSource()
                ) as T
            }
            modelClass.isAssignableFrom(VoteViewModel::class.java) -> {
                VoteViewModel(getVoteDataSource()) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(getBreedRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}