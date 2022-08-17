package com.windranger.reminder.di

import android.content.Context
import com.windranger.reminder.App
import com.windranger.reminder.model.MyObjectBox
import com.windranger.reminder.util.SessionManager
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton



/**
 * Created by didik on 9/15/17.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideSessionManager(context: Context): SessionManager = SessionManager(context)

    @Provides
    @Singleton
    fun provideBoxStore(context: Context): BoxStore {
        return MyObjectBox.builder().androidContext(context).build()
    }
}