package com.gksoftwaresolutions.catapp.view.detailBreed

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.gksoftwaresolutions.catapp.CatApplication
import com.gksoftwaresolutions.catapp.R
import com.gksoftwaresolutions.catapp.databinding.ActivityDetailBinding
import com.gksoftwaresolutions.catapp.model.BreedItem
import com.gksoftwaresolutions.catapp.viewModel.DetailViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var itemBreed: BreedItem

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
            val item = it[0]
            itemBreed = item
            binding.layoutContent.name.text = item.name
            binding.layoutContent.description.text = item.description
            binding.layoutContent.origin.text = item.origin
            binding.layoutContent.temperament.text = item.temperament
            Glide.with(this)
                .load("https://mycatdaily.files.wordpress.com/2019/05/what-is-a-group-of-cats-called-ao-long.jpg")
                .centerCrop()
                .into(binding.breedImage)
        })

        binding.fab.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            builder.setStartAnimations(this, android.R.anim.fade_in, android.R.anim.fade_out);
            builder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            val colorInt: Int = Color.parseColor("#0091ea")
            builder.setToolbarColor(colorInt)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(itemBreed.wikipedia_url))
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