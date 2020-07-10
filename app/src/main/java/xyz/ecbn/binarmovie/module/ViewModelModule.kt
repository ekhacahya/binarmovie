package xyz.ecbn.binarmovie.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import xyz.ecbn.binarmovie.viewmodel.MovieViewModel

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}