package com.techieasif.miwok;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;

    //AudioChanged Listener
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                    || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //temporary loss of audio, so pausing media player
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //audio gained back now start playing
                mediaPlayer.start();

            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //Permanent loss of audio focus, release media player resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordModel> words = new ArrayList<WordModel>();
        words.add(new WordModel("weṭeṭṭi", "red", R.drawable.color_red, R.raw.color_red));
        words.add(new WordModel("chokokki", "green", R.drawable.color_green, R.raw.color_green));
        words.add(new WordModel("ṭakaakki", "brown", R.drawable.color_brown, R.raw.color_brown));
        words.add(new WordModel("ṭopoppi", "gray", R.drawable.color_gray, R.raw.color_gray));
        words.add(new WordModel("kululli", "black", R.drawable.color_black, R.raw.color_black));
        words.add(new WordModel("kelelli", "white", R.drawable.color_white, R.raw.color_white));
        words.add(new WordModel("ṭopiisә", "dusty yellow", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new WordModel("chiwiiṭә", "mustered yellow", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter itemsAdapter =
                new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setBackgroundColor(getResources().getColor(R.color.category_colors));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, words.get(position).getAudioResource());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        }
        );

        listView.setAdapter(itemsAdapter);

    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            //Abandoning AudioFocus when playback completes so that we no longer get system callbacks.
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //releasing mediaPlayer resources when activity stopped.
        releaseMediaPlayer();
    }
}