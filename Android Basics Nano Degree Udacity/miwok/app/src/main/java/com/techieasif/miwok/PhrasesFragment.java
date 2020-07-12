package com.techieasif.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhrasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragment extends Fragment {

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



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhrasesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhrasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhrasesFragment newInstance(String param1, String param2) {
        PhrasesFragment fragment = new PhrasesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordModel> words = new ArrayList<WordModel>();
        words.add(new WordModel("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going));
        words.add(new WordModel("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name));
        words.add(new WordModel("oyaaset...", "My name is...", R.raw.phrase_my_name_is));
        words.add(new WordModel("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling));
        words.add(new WordModel("kuchi achit", "I’m feeling good." , R.raw.phrase_im_feeling_good));
        words.add(new WordModel("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming));
        words.add(new WordModel("hәә’ әәnәm", "Yes, I’m coming.", R.raw.phrase_yes_im_coming));
        words.add(new WordModel("әәnәm", "I’m coming.", R.raw.phrase_im_coming));
        words.add(new WordModel("yoowutis", "Let’s go.", R.raw.phrase_lets_go));
        words.add(new WordModel("әnni'nem", "Come here.", R.raw.phrase_come_here));

        WordAdapter itemsAdapter =
                new WordAdapter(getActivity(), words, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
//        listView.setBackgroundColor(getResources().getColor(R.color.category_phrases));

        listView.setAdapter(itemsAdapter);

        //playing audio
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioResource());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });



        return rootView;
    }

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
    public void onStop() {
        super.onStop();
        //releasing mediaPlayer resources when activity stopped.
        releaseMediaPlayer();
    }
}