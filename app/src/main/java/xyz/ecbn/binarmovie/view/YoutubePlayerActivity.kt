package xyz.ecbn.binarmovie.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_youtube_player.*
import xyz.ecbn.binarmovie.R

class YoutubePlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player)

        youtubePlayer.play("UcmZN0Mbl04")
    }
}
