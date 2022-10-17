package com.example.mediaplayerdemo.model

import com.google.android.exoplayer2.MediaItem


data class PlayerUiState(
    val mediaState: MediaState,
    val mediaItem: MediaItem? = null,
    var isMediaControllerShown: Boolean = false,
) {
    companion object {
        val INITIAL = PlayerUiState(
            MediaState.PREPARE,
            null,
        )
    }
}


enum class MediaState(
    val isMediaReady: Boolean,
    val playWhenReady: Boolean,
) {
    PREPARE(true, false),
    READY(true, false),
    PLAY(true, true),
    PAUSE(true, false),
}
