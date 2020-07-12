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

public class FamilyActivity extends AppCompatActivity {
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
        words.add(new WordModel("әpә", "Father", R.drawable.family_father, R.raw.family_father));
        words.add(new WordModel("әṭa", "Mother", R.drawable.family_mother, R.raw.family_mother));
        words.add(new WordModel("angsi", "Son", R.drawable.family_son, R.raw.family_son));
        words.add(new WordModel("tune", "Daughter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new WordModel("taachi", "Older Brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new WordModel("chalitti", "younger Brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new WordModel("teṭe", "older Sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new WordModel("kolliti", "younger Sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new WordModel("ama", "grandmother", R.drawable.family_grandmother,  R.raw.family_grandmother));
        words.add(new WordModel("paapa", "grandfather", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter itemsAdapter =
                new WordAdapter(this, words,R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setBackgroundColor(getResources().getColor(R.color.category_family));

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, words.get(position).getAudioResource());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
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