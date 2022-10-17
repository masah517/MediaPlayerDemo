package com.example.mediaplayerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mediaplayerdemo.compose.molecules.ItemDescriptionScreen
import com.example.mediaplayerdemo.ui.theme.MediaPlayerDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MediaPlayerDemoTheme {
                ItemDescriptionScreen(viewModel = viewModel)
            }
        }
    }
}
