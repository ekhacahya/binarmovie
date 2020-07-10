package xyz.ecbn.binarmovie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.fragment_info_movie.*
import net.idik.lib.slimadapter.SlimAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.ecbn.binarmovie.BuildConfig
import xyz.ecbn.binarmovie.KEY_MOVIE
import xyz.ecbn.binarmovie.R
import xyz.ecbn.binarmovie.model.Actor
import xyz.ecbn.binarmovie.model.Image
import xyz.ecbn.binarmovie.model.Movie
import xyz.ecbn.binarmovie.model.Video
import xyz.ecbn.binarmovie.viewmodel.MovieViewModel

/**
 * A simple [Fragment] subclass.
 */
class InfoMovieFragment : Fragment() {
    private val mMovieViewModel: MovieViewModel by viewModel()
    private val mGlide: RequestManager by inject()

    fun newInstance(movie: Movie): InfoMovieFragment {
        val args = Bundle()
        args.putParcelable(KEY_MOVIE, movie)
        val fragment = InfoMovieFragment()
        fragment.arguments = args
        return fragment
    }

    private var mPosterAdapter = SlimAdapter.create()
        .register<Image>(R.layout.item_poster) { data, injector ->
            injector.with<ImageView>(R.id.imageView) {
                mGlide
                    .load("${BuildConfig.BASE_URL_IMAGE}w154${data.file_path}")
                    .into(it)
            }
        }
    private var mActorAdapter = SlimAdapter.create()
        .register<Actor>(R.layout.item_actor) { data, injector ->
            injector.text(R.id.tvName, data.name)
                .text(R.id.tvCharacter, data.character)
                .with<ImageView>(R.id.ivActor) {
                    mGlide
                        .load("${BuildConfig.BASE_URL_IMAGE}w154${data.profile_path}")
                        .into(it)
                }
        }

    private var mTrailerAdapter = SlimAdapter.create()
        .register<Video>(R.layout.item_trailer) { data, injector ->
            injector.text(R.id.tvTitle, data.name)
                .clicked(R.id.ivPlay) {
                    val bundle = bundleOf("video" to data)
                    it.findNavController().navigate(
                        R.id.action_detailMovieFragment_to_youtubeFragment,
                        bundle
                    )
                }
                .with<ImageView>(R.id.ivTrailer) {
                    mGlide
                        .load("https://img.youtube.com/vi/${data.key}/0.jpg")
                        .into(it)
                }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(KEY_MOVIE)

        initView()

        movie?.let { it ->
            mMovieViewModel.getMovie(it.id)
            mMovieViewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
                tvStoryline.text = movie.overview
                tvMovieLength.text = "${movie.runtime}${getString(R.string.detail_length)}"
                tvMovieTitle.text = it.original_title.plus(" (${it.release_date?.substring(0, 4)})")
                tvMovieGenre.text = movie.genres?.joinToString(separator = " ", transform = {
                    "#${it.name}"
                })

                mPosterAdapter.updateData(movie.images?.posters)
                mActorAdapter.updateData(movie.credits?.cast)
                mTrailerAdapter.updateData(movie.videos?.results)
            })
        }
    }

    private fun initView() {
        rvPoster.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvPoster.adapter = mPosterAdapter
        rvPoster.isNestedScrollingEnabled = false

        rvActor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvActor.adapter = mActorAdapter
        rvPoster.isNestedScrollingEnabled = false

        rvTrailer.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTrailer.adapter = mTrailerAdapter
        rvTrailer.isNestedScrollingEnabled = false
    }

}
