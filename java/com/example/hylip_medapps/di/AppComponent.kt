package com.windranger.reminder.di

import com.windranger.reminder.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by didik on 9/15/17.
 */
@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class),
    (BuildersModule::class), (NetModule::class)])
interface AppComponent {
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}