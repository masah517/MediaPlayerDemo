package com.example.mediaplayerdemo.compose.molecules

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mediaplayerdemo.Event
import com.example.mediaplayerdemo.PlayerViewModel
import com.example.mediaplayerdemo.R
import com.example.mediaplayerdemo.compose.atoms.VideoPlayer

@Composable
fun ItemDescriptionScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Log.d("MyMsg", "Current MediaItem State ${uiState.mediaState}")

    LazyColumn(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.emitEvent(Event.onMediaPlayerClickEvent)
                    },
                contentAlignment = Alignment.Center,
            ) {

                VideoPlayer(
                    mediaItem = uiState.mediaItem,
                    onStateReadyCallBack = viewModel::emitEvent,
                    onClickPlayerViewCallback = viewModel::emitEvent,
                    playWhenReady = uiState.mediaState.playWhenReady,
                    showController = uiState.isMediaControllerShown,
                    player = viewModel.player,
                )
                /*
                IconButton(onClick = {
                    viewModel.emitEvent(Event.onMediaPlayerClickEvent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                        contentDescription = "favorite",
                        tint = Color.Blue,
                        modifier = Modifier.size(60.dp),
                    )
                }*/
            }
        }
    }
}
