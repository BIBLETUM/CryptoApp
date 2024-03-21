package com.example.cryptoapp.di

import com.example.cryptoapp.data.Workers.ChildWorkerFactory
import com.example.cryptoapp.data.Workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ModuleWorker {

    @IntoMap
    @Binds
    @WorkerKey(RefreshDataWorker::class)
    fun provide(impl: RefreshDataWorker.Factory): ChildWorkerFactory

}