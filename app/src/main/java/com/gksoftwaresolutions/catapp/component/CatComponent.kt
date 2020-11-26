package com.gksoftwaresolutions.catapp.component

import com.gksoftwaresolutions.catapp.view.MainActivity
import com.gksoftwaresolutions.catapp.view.categories.BreedFragment
import com.gksoftwaresolutions.catapp.view.detailBreed.DetailActivity
import com.gksoftwaresolutions.catapp.view.home.HomeFragment
import com.gksoftwaresolutions.catapp.view.votes.VoteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactory::class])
interface CatComponent {
    fun inject(inject: MainActivity)
    fun inject(inject: BreedFragment)
    fun inject(inject: HomeFragment)
    fun inject(inject: VoteFragment)
    fun inject(inject: DetailActivity)
}