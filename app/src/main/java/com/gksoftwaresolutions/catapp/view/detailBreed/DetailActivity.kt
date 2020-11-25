package com.gksoftwaresolutions.catapp.view.detailBreed

import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gksoftwaresolutions.catapp.CatApplication
import com.gksoftwaresolutions.catapp.R
import com.gksoftwaresolutions.catapp.databinding.ActivityDetailBinding
import com.gksoftwaresolutions.catapp.viewModel.DetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Inject
    lateinit var mViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        (applicationContext as CatApplication).catComponent.inject(this)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        mViewModel.observableBreedItem().observe(this, {
            binding.layoutContent.name.text = it.name
            binding.layoutContent.description.text = it.description
            binding.layoutContent.origin.text = it.origin
            binding.layoutContent.temperament.text = it.temperament
            Glide.with(this)
                .load("https://mycatdaily.files.wordpress.com/2019/05/what-is-a-group-of-cats-called-ao-long.jpg")
                .centerCrop()
                .into(binding.breedImage)
        })

        binding.fab.setOnClickListener {

        }

        mViewModel.observableErrorFind().observe(this, {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error to find")
                .setMessage("The breed with name, not find, try again with other name id.")
                .setPositiveButton("Accept") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }.create()
                .show()
        })
    }

    override fun onStart() {
        super.onStart()
        if (intent.extras != null) {
            val nameFind = intent!!.extras!!.getString("breed_name")
            binding.toolbarLayout.title = nameFind
            mViewModel.findBreed(nameFind!!)
        }
    }
}