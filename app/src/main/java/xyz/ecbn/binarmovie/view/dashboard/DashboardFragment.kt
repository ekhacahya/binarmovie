package xyz.ecbn.binarmovie.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_empty.*
import net.idik.lib.slimadapter.SlimAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.ecbn.binarmovie.BuildConfig
import xyz.ecbn.binarmovie.R
import xyz.ecbn.binarmovie.data.NetworkState
import xyz.ecbn.binarmovie.data.Status
import xyz.ecbn.binarmovie.model.Genre
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.utils.EndlessRecyclerViewScrollListener
import xyz.ecbn.binarmovie.utils.hide
import xyz.ecbn.binarmovie.utils.show
import xyz.ecbn.binarmovie.viewmodel.MovieViewModel


/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment(), Observer<NetworkState> {

    private val mGlide: RequestManager by inject()
    private val mMovieViewModel: MovieViewModel by viewModel()
    private var mSelectedGenres = arrayListOf<Int>()
    private var mListMovies: ArrayList<Movie> = arrayListOf()
    private lateinit var mScrollListener: EndlessRecyclerViewScrollListener
    private var mPage: Int = 1

    private var mMovieAdapter = SlimAdapter.create()
        .register<Movie>(R.layout.item_movie_vertical) { data, injector ->
            injector.text(R.id.tvName, data.original_title)
                .text(R.id.tvReleaseDate, data.release_date?.substring(0, 4))
                .text(R.id.tvVoteAverage, "${data.vote_average}")
                .with<ImageView>(R.id.imageView) {
                    mGlide
                        .load("${BuildConfig.BASE_URL_IMAGE}w185${data.poster_path}")
                        .into(it)
                }
                .clicked(R.id.parentMovie) {
                    val bundle = bundleOf("movie" to data)
                    it.findNavController().navigate(
                        R.id.action_singleMovieFragment_to_detailMovieFragment,
                        bundle
                    )
                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        mMovieViewModel.genres.observe(viewLifecycleOwner, Observer {
            addChipView(it)
        })
        mMovieViewModel.movies.observe(viewLifecycleOwner, Observer {
            if (mPage == 1) {
                mListMovies.clear()
            }
            mListMovies.addAll(it)
            mMovieAdapter.updateData(mListMovies)
        })
        mMovieViewModel.network.observe(viewLifecycleOwner, this)
    }

    private fun initView() {
        dashboardToolbar.title = getString(R.string.app_name)

        val layoutManager = GridLayoutManager(context, 2)
        rvMovies.layoutManager = layoutManager
        rvMovies.adapter = mMovieAdapter

        srlMovies.setOnRefreshListener {
            getMovies()
        }

        mScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPage = page
                mMovieViewModel.getMovies(genre = mSelectedGenres.joinToString(), page = mPage)
            }
        }
        rvMovies.addOnScrollListener(mScrollListener)

        getMovies()
    }

    private fun addChipView(genres: List<Genre>) {
        if (cgGenre.childCount == 0) {
            val inflater = LayoutInflater.from(context)
            genres.map {
                val chip = inflater.inflate(R.layout.item_chip_genre, null, false) as Chip
                chip.text = it.name
                chip.isChecked = mSelectedGenres.contains(it.id)
                chip.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        mSelectedGenres.add(it.id)
                    } else {
                        mSelectedGenres.remove(it.id)
                    }
                    getMovies()
                }
                cgGenre.addView(chip)
            }
        }
    }

    private fun getMovies() {
        mScrollListener.resetState()
        mPage = 1
        mMovieAdapter.updateData(arrayListOf<Movie>())
        mMovieViewModel.getMovies(genre = mSelectedGenres.joinToString(), page = mPage)
        mMovieViewModel.getGenres()
    }


    override fun onChanged(t: NetworkState?) {
        srlMovies.isRefreshing = t?.status == Status.RUNNING

        when (t?.status) {
            Status.NO_RESULT -> {
                layout_error.show()
                tvErrorMessage.text = t.msg
            }
            Status.RUNNING -> {
                layout_error.hide()
            }
            Status.FAILED -> {
                layout_error.show()
                tvErrorMessage.text = t.msg
            }
            Status.SUCCESS -> {
                layout_error.hide()
            }
        }
    }
}
