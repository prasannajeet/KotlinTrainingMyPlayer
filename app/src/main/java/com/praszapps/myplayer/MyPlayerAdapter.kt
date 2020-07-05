package com.praszapps.myplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.praszapps.myplayer.databinding.ViewMediaItemBinding
import kotlin.properties.Delegates

class MyPlayerAdapter(private val listener : MediaItemClickListener) : RecyclerView.Adapter<MyPlayerAdapter.MyPlayerItemVH>() {

    var itemList : List<MediaItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlayerItemVH
            = MyPlayerItemVH(ViewMediaItemBinding.inflate(parent.getLayoutInflater()))

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: MyPlayerItemVH, position: Int) = run {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener { listener(itemList[position]) }
    }

    class MyPlayerItemVH(private val binding: ViewMediaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the item values to the binding layout
         * @since v1
         */
        fun bind (item: MediaItem) {

            with(binding) {
                mediaTitle.text = item.title
                mediaThumb.loadUrl(item.url)
                mediaVideoIndicator.visibility = when(item.type) {
                    MediaItem.Type.PHOTO -> View.INVISIBLE
                    MediaItem.Type.VIDEO -> View.VISIBLE
                }
            }
        }
    }
}