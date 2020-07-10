package xyz.ecbn.binarmovie.module

import org.koin.dsl.module
import xyz.ecbn.binarmovie.data.repo.MovieRepository

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
val repositoryModule = module {
    single { MovieRepository(get()) }
}