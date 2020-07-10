package xyz.ecbn.binarmovie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_youtube.*
import xyz.ecbn.binarmovie.R

/**
 * A simple [Fragment] subclass.
 */
class YoutubeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { YoutubeFragmentArgs.fromBundle(it) }
        val video = args?.video

        youtubePlayer.play(video?.key.toString())
    }

}
