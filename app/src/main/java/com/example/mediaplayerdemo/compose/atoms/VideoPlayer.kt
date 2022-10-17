package com.example.mediaplayerdemo.compose.atoms

import android.animation.ObjectAnimator
import android.content.ComponentName
import android.view.View
import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.mediaplayerdemo.DcPlayerView
import com.example.mediaplayerdemo.Event
import com.example.mediaplayerdemo.PlayerService
import com.example.mediaplayerdemo.R
import kotlinx.coroutines.*

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier.fillMaxSize(),
    mediaItem: MediaItem?,
    onStateReadyCallBack: (Event) -> Unit,
    onClickPlayerViewCallback: (Event) -> Unit,
    playWhenReady: Boolean = false,
    showController: Boolean,
) {
    val ANIMATION_DURATION = 300L
    val SHOW_TIMEOUT_DURATION = 3000L

    val context = LocalContext.current

    val playerView = remember {
        DcPlayerView(context = context)
    }

    var mediaController by remember {
        mutableStateOf<MediaController?>(null)
    }

    LaunchedEffect(showController) {


        val autoFadeAway = async {
            if(showController) {
                delay(SHOW_TIMEOUT_DURATION + ANIMATION_DURATION)
                onClickPlayerViewCallback(Event.onPlayerViewScreenClickEvent)
            }
        }

        if (showController) {
            /*
            ObjectAnimator.ofFloat(
                playerView.binding.playerView.findViewById<ImageView>(R.id.fullScreen),
                ConstraintLayout.ALPHA,
                0f,
                1f
            ).run {
                duration = ANIMATION_DURATION
                doOnStart {
                    playerView.binding.playerView.findViewById<ImageView>(R.id.fullScreen).visibility = View.VISIBLE
                }
                start()
            }
        } else {
            ObjectAnimator.ofFloat(
                playerView.binding.playerView.findViewById<ImageView>(R.id.fullScreen),
                ConstraintLayout.ALPHA,
                1f,
                0f
            ).run {
                duration = ANIMATION_DURATION
                doOnEnd {
                    playerView.binding.playerView.findViewById<ImageView>(R.id.fullScreen).visibility = View.GONE
                    autoFadeAway.cancel()
                }
                start()
            }*/
        }

    }

    LaunchedEffect(mediaItem) {

        if (mediaItem != null) {
            if (mediaController == null) {
                val sessionToken =
                    SessionToken(context, ComponentName(context, PlayerService::class.java))
                mediaController = MediaController.Builder(context, sessionToken)
                    .buildAsync()
                    .await()


                mediaController?.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)

                        when (playbackState) {
                            Player.STATE_READY -> {
                                onStateReadyCallBack(Event.onStateReadyEvent)
                            }

                        }
                    }
                })

                playerView.binding.playerView.player = mediaController
                playerView.binding.playerView.controllerHideOnTouch = false

                /*
                playerView.binding.player.videoSurfaceView?.setOnClickListener {
                    Log.d("MyMsg", "Click")
                    onClickPlayerViewCallback(Event.onPlayerViewScreenClickEvent)
                }*/
                //playerView.binding.player.findViewById<ImageView>(R.id.fullScreen).visibility = View.GONE

                mediaController?.prepare()

                if (mediaController?.currentMediaItem != mediaItem) {
                    mediaController?.setMediaItem(mediaItem)
                }
            }
        }
    }

    mediaController?.playWhenReady = playWhenReady

    DisposableEffect(
        AndroidView(
            factory = { playerView },
            modifier = modifier,
        )
    ) {
        onDispose {
            mediaController?.release()
        }
    }
}
