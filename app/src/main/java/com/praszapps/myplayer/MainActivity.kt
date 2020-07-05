package com.praszapps.myplayer

import android.app.Activity
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.praszapps.myplayer.MediaItem.Type.PHOTO
import com.praszapps.myplayer.MediaItem.Type.VIDEO
import com.praszapps.myplayer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), Loggable {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter : MyPlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        //job = SupervisorJob() // Supervisor Job won't let CRs be dependent

        logDebug("Debugged")
        logError("Errored")
        log("Warned", true)
        log("Debugged Again")

        //startActivity<MainActivity>() --> How to use reified and inline modifiers for generics

        //recycler.adapter = MyPlayerAdapter(getItems()) // Using Kotlin Android Extensions

        adapter = MyPlayerAdapter() {
            startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it.id)
        }
        adapter.itemList = MediaProvider.getItems()
        binding.recycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        //job.cancel() // If activity finishes, cancel all CRs
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val filter = when(item.itemId) {
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            else -> Filter.None
        }

        updateItems(filter)

        return super.onOptionsItemSelected(item)
    }


    private fun updateItems(filter: Filter = Filter.None) {
        lifecycleScope.launch {

            binding.progress.visibility = View.VISIBLE

            adapter.itemList  =  with(MediaProvider.getItems()) {
                when(filter) {
                    is Filter.ByType -> this.asSequence().filter { it.type == filter.type }.toList()
                    Filter.None -> this
                }
            }

            binding.progress.visibility = View.GONE
        }
    }
}