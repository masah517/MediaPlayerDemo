package com.example.mediaplayerdemo.di

import com.example.mediaplayerdemo.RichRepository
import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VideoModule {
    @Provides
    @ViewModelScoped
    fun provideRichRepository(@ApplicationContext context: Context) : RichRepository = RichRepository(context)

    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(@ApplicationContext context: Context): ExoPlayer = lazy{
         ExoPlayer.Builder(context)
            .build()
    }.value
}
