package com.praszapps.myplayer

import androidx.annotation.NonNull

// In Data class, all args in constructor are properties
data class MediaItem (@NonNull val id: Int, @NonNull val title: String, @NonNull val url: String, @NonNull val type: Type) {

    enum class Type {
        PHOTO, VIDEO
    }
}