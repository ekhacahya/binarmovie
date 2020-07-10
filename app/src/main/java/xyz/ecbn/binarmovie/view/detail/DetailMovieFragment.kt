package xyz.ecbn.binarmovie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.RequestManager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.detail_movie_fragment.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.ecbn.binarmovie.BuildConfig
import xyz.ecbn.binarmovie.R
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.utils.prettyCount
import xyz.ecbn.binarmovie.view.dashboard.DashboardFragmentArgs
import xyz.ecbn.binarmovie.viewmodel.MovieViewModel

class DetailMovieFragment : Fragment() {

    private val mGlide: RequestManager by inject()
    private val mMovieViewModel: MovieViewModel by viewModel()
    private lateinit var mMovieDetailAdapter: MovieDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { DashboardFragmentArgs.fromBundle(it) }
        val movieData = args?.movie

        movieData?.let {
            setBaseData(movieData)
            mMovieViewModel.getMovie(it.id)
            mMovieViewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
                tvMetaScore.text = movie.vote_average.toString()
            })
        }


    }

    private fun setBaseData(it: Movie) {
        toolbar.title = it.original_title

        scrollView.isFillViewport = true

        mMovieDetailAdapter =
            MovieDetailAdapter(this, it)
        vpMovie.adapter = mMovieDetailAdapter
        vpMovie.isUserInputEnabled = false
        TabLayoutMediator(tlMovie, vpMovie) { tab, position ->
            if (position == 0) tab.text = "Information"
            else tab.text = "Reviews"
        }.attach()

        mGlide
            .load(BuildConfig.BASE_URL_IMAGE.plus("w780") + it.backdrop_path)
            .into(ivHeaderImage)
        tvMetaScore.text = it.vote_average.toString()
        tvTotalVotes.text = "${it.vote_count?.prettyCount()} Votes"
    }

}
