package com.pavlig43.retromeet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Dispatcher(DispatcherType.DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(DispatcherType.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(DispatcherType.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(DispatcherType.IMMEDIATE)
    fun provideIMMEDIATEDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}

@Qualifier
annotation class Dispatcher(val type: DispatcherType)

enum class DispatcherType {
    DEFAULT,
    IO,
    MAIN,
    IMMEDIATE
}
