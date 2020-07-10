package xyz.ecbn.binarmovie.view.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.ecbn.binarmovie.model.Movie

/**
 * BinarMovie Created by ecbn on 09/07/20.
 */
class MovieDetailAdapter(fragment: Fragment, movie: Movie) : FragmentStateAdapter(fragment) {

    private val movie: Movie by lazy {
        return@lazy movie
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0)
            return InfoMovieFragment().newInstance(movie)
        return ReviewsFragment().newInstance(movie)
    }


}