package com.benaya.favorite.di

import android.content.Context
import com.benaya.favorite.FavoriteFragment
import com.benaya.moviex.di.FavoriteModuleDependency
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependency::class]
)
interface FavoriteComponent {
    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependency): Builder
        fun build(): FavoriteComponent
    }
}