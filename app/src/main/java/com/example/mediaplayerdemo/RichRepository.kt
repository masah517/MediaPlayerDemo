package com.example.mediaplayerdemo

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RichRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getVideoFromRemote(): MediaItem {
        val requestMetadata = MediaItem.RequestMetadata.Builder()
            .setMediaUri(Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"))
            .build()

        return MediaItem.Builder()
            .setRequestMetadata(requestMetadata)
            .build()
    }

    fun getVideoFromLocal(fileName: String): MediaItem {
        val uri = Uri.parse("asset:///${fileName}")

        val requestMetadata = MediaItem.RequestMetadata.Builder()
            .setMediaUri(uri)
            .build()

        val assetFileDescriptor = context.assets.openFd(fileName)
        val metadataRetriever = MediaMetadataRetriever()
        metadataRetriever.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )

        val mediaMetadata = MediaMetadata.Builder()
            .setTitle(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE))
            .setArtist(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST))
            .build()

        return MediaItem.Builder()
            .setRequestMetadata(requestMetadata)
            .setMediaMetadata(mediaMetadata)
            .build()
    }
}
