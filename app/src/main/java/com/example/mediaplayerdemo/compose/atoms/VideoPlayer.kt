package com.example.mediaplayerdemo.compose.atoms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mediaplayerdemo.DcPlayerView
import com.example.mediaplayerdemo.Event
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier.fillMaxSize(),
    mediaItem: MediaItem?,
    onStateReadyCallBack: (Event) -> Unit,
    onClickPlayerViewCallback: (Event) -> Unit,
    playWhenReady: Boolean = false,
    showController: Boolean,
    player: ExoPlayer,
) {

    val context = LocalContext.current

    val dcPlayerView = remember {
        //PlayerView(context)
        DcPlayerView(context = context)
    }

    LaunchedEffect(mediaItem) {

        val playerView = dcPlayerView.binding.playerView

        if (mediaItem != null) {
            playerView.player = player
            player.addMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }
    }

    DisposableEffect(
        AndroidView(
            factory = {dcPlayerView},
            modifier = modifier,
        )
    ) {
        onDispose {
            player.release()
        }
    }
}
