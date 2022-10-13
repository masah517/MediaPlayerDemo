package com.example.mediaplayerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.mediaplayerdemo.databinding.ActivityMainBinding
import com.example.mediaplayerdemo.ui.theme.MediaPlayerDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: ExoPlayer
    val mediaItems = arrayListOf<MediaItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // PlayerViewを表示
        val playerView = PlayerView(this)
        setContentView(playerView)

        mediaPlayer = ExoPlayer.Builder(this).build()
        playerView.player = mediaPlayer

        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        val mediaItemTwo = MediaItem.fromUri("https://samplelib.com/lib/preview/mp4/sample-10s.mp4")
        mediaItems.add(mediaItem)
        mediaItems.add(mediaItemTwo)

        mediaPlayer.setMediaItems(mediaItems)
        mediaPlayer.playWhenReady = true
    }

    override fun onStart() {
        super.onStart()

        //mediaPlayer.prepare()
        //mediaPlayer.play()
    }

    override fun onStop() {
        super.onStop()

        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
