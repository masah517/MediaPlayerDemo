package com.example.mediaplayerdemo

import androidx.lifecycle.ViewModel
import com.example.mediaplayerdemo.model.MediaState
import com.example.mediaplayerdemo.model.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    richRepository: RichRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState.INITIAL)
    val uiState = _uiState

    init {
        _uiState.update {
            it.copy(
                mediaItem = richRepository.getVideoFromRemote()
            )
        }
    }

    fun emitEvent(event: Event) {
        when (event) {
            Event.onStateReadyEvent -> {
                _uiState.update {
                    it.copy(
                        mediaState = MediaState.READY
                    )
                }
            }

            Event.onMediaPlayerClickEvent -> {
                _uiState.update {
                    it.copy(
                        mediaState = if (it.mediaState.playWhenReady.not()) MediaState.PLAY else MediaState.PAUSE
                    )
                }
            }

            Event.onPlayerViewScreenClickEvent -> {
                _uiState.update { player ->
                    player.copy(
                        isMediaControllerShown = player.isMediaControllerShown.not()
                    )
                }
            }
        }
    }
}

sealed interface Event {
    object onStateReadyEvent : Event
    object onMediaPlayerClickEvent : Event
    object onPlayerViewScreenClickEvent : Event
}
