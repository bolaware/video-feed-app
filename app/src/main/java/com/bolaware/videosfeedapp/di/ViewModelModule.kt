package com.bolaware.videosfeedapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bolaware.videosfeedapp.HomeFragment
import com.bolaware.videosfeedapp.MainActivity
import com.bolaware.videosfeedapp.network.NetworkRepo
import com.bolaware.videosfeedapp.viewmodel.HomeViewModel
import com.danikula.videocache.HttpProxyCacheServer
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Module(includes = [
    ViewModelModule.ProvideViewModel::class
])
abstract class ViewModelModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory =
            AppViewModelFactory(providers)
    }

    @ContributesAndroidInjector(
        modules = [
            InjectViewModel::class
        ]
    )
    abstract fun bind(): HomeFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        fun provideHomeViewModel(networkRepo : NetworkRepo, httpProxyCacheServer: HttpProxyCacheServer): ViewModel =
            HomeViewModel(networkRepo, httpProxyCacheServer)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideHomeViewModel(
            factory: ViewModelProvider.Factory,
            target: HomeFragment
        ) = ViewModelProviders.of(target, factory).get(HomeViewModel::class.java)
    }
}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
    val value: KClass<out ViewModel>
)