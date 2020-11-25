package com.gksoftwaresolutions.catapp.component

import com.gksoftwaresolutions.catapp.view.MainActivity
import com.gksoftwaresolutions.catapp.view.ui.categories.BreedFragment
import com.gksoftwaresolutions.catapp.view.ui.home.HomeFragment
import com.gksoftwaresolutions.catapp.view.ui.votes.VoteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactory::class])
interface CatComponent {
    fun inject(inject: MainActivity)
    fun inject(inject: BreedFragment)
    fun inject(inject: HomeFragment)
    fun inject(inject: VoteFragment)
}