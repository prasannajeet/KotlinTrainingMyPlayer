package com.praszapps.myplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.praszapps.myplayer.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemId = intent.getIntExtra(EXTRA_ID, -1)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val items = MediaProvider.getItems()
                val item = items.firstOrNull {
                    it.id == itemId
                }

                item?.let {
                    supportActionBar?.title = it.title

                    binding.detailThumb.loadUrl(it.url)

                    binding.detailVideoIndicator.visibility = when(it.type) {
                        MediaItem.Type.PHOTO -> View.GONE
                        MediaItem.Type.VIDEO -> View.VISIBLE
                    }
                }
            }
        }
    }
}