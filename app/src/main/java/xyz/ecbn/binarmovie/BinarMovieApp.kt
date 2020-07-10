package xyz.ecbn.binarmovie

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import xyz.ecbn.binarmovie.module.*

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
class BinarMovieApp : Application() {
    private val calConfig: CalligraphyConfig by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BinarMovieApp)
            modules(
                listOf(
                    retrofitModule,
                    viewModelModule,
                    appModule,
                    glideModule,
                    repositoryModule
                )
            )
        }
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        calConfig
                    )
                )
                .build()
        )
    }
}