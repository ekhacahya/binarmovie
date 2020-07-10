package xyz.ecbn.binarmovie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.layout_empty.*
import net.idik.lib.slimadapter.SlimAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.ecbn.binarmovie.KEY_MOVIE
import xyz.ecbn.binarmovie.R
import xyz.ecbn.binarmovie.data.NetworkState
import xyz.ecbn.binarmovie.data.Status
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.model.Review
import xyz.ecbn.binarmovie.utils.EndlessRecyclerViewScrollListener
import xyz.ecbn.binarmovie.utils.hide
import xyz.ecbn.binarmovie.utils.show
import xyz.ecbn.binarmovie.viewmodel.MovieViewModel

class ReviewsFragment : Fragment(), Observer<NetworkState> {

    private val mMovieViewModel: MovieViewModel by viewModel()
    private lateinit var mScrollListener: EndlessRecyclerViewScrollListener
    private var mPage: Int = 1
    private var mMovie: Movie? = null
    private var mListReviews: ArrayList<Review> = arrayListOf()

    fun newInstance(movie: Movie): ReviewsFragment {
        val args = Bundle()
        args.putParcelable(KEY_MOVIE, movie)
        val fragment = ReviewsFragment()
        fragment.arguments = args
        return fragment
    }

    private var mReviewAdapter = SlimAdapter.create()
        .register<Review>(R.layout.item_movie_review) { data, injector ->
            injector.text(R.id.tvReviewAuthor, data.author)
            injector.text(R.id.tvReviewContent, data.content)
                .clicked(R.id.parentMovie) {

                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMovie = arguments?.getParcelable(KEY_MOVIE)

        initView()

        mMovie?.let {
            getReviews()
            mMovieViewModel.reviews.observe(viewLifecycleOwner, Observer { reviews ->
                if (mPage == 1) {
                    mListReviews.clear()
                }
                mListReviews.addAll(reviews)
                mReviewAdapter.updateData(mListReviews)
            })
        }
        mMovieViewModel.network.observe(viewLifecycleOwner, this)
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context)
        rvReviews.layoutManager = layoutManager
        rvReviews.adapter = mReviewAdapter
        mScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPage = page
                mMovie?.id?.let { mMovieViewModel.getMovieReviews(mPage, it) }
            }
        }
        rvReviews.addOnScrollListener(mScrollListener)

        srlReview.setOnRefreshListener { getReviews() }
    }

    private fun getReviews() {
        mScrollListener.resetState()
        mPage = 1
        mReviewAdapter.updateData(arrayListOf<Review>())
        mMovie?.id?.let { mMovieViewModel.getMovieReviews(mPage, it) }
    }

    override fun onChanged(t: NetworkState?) {
        srlReview.isRefreshing = t?.status == Status.RUNNING

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
