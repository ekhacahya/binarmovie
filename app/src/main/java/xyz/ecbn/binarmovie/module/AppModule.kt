package xyz.ecbn.binarmovie.module

import io.github.inflationx.calligraphy3.CalligraphyConfig
import org.koin.dsl.module
import xyz.ecbn.binarmovie.DEFAULT_FONT
import xyz.ecbn.binarmovie.R

/**
 * BinarMovie Created by ecbn on 08/07/20.
 */
val appModule = module {
    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath(DEFAULT_FONT)
            .setFontAttrId(R.attr.fontPath)
            .build()
    }
}