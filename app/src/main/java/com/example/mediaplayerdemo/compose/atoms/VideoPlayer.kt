package com.example.mediaplayerdemo.compose.atoms

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.mediaplayerdemo.DcPlayerView
import com.example.mediaplayerdemo.Event
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier.fillMaxWidth(),
    mediaItem: MediaItem?,
    onStateReadyCallBack: (Event) -> Unit,
    onClickPlayerViewCallback: (Event) -> Unit,
    playWhenReady: Boolean,
    showController: Boolean,
    player: ExoPlayer,
) {

    val context = LocalContext.current

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val dcPlayerView = remember {
        //PlayerView(context)
        DcPlayerView(context = context)
    }

    LaunchedEffect(mediaItem) {

        val playerView = dcPlayerView.binding.playerView

        player.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)

                when(playbackState){
                    Player.STATE_READY-> {
                        onStateReadyCallBack(Event.onStateReadyEvent)
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                Log.d("MyMsg", "Play/Pause")
            }
        })

        if (mediaItem != null) {
            playerView.player = player
            player.prepare()
            if(player.currentMediaItem != mediaItem){
                player.setMediaItem(mediaItem)
            }
        }
    }
    player.playWhenReady = playWhenReady


    AndroidView(
        factory = { dcPlayerView },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    player.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                }
                Lifecycle.Event.ON_DESTROY -> {
                    player.release()
                }
                else -> Unit
            }
        },
        modifier = modifier.aspectRatio(16F/9F),
    )
}
