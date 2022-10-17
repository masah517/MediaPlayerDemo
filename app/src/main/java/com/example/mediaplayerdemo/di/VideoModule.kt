package com.example.mediaplayerdemo.di

import com.example.mediaplayerdemo.RichRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object VideoModule {
    @ActivityScoped
    @Provides
    fun provideRichRepository(context: Context) : RichRepository = RichRepository(context)
}
