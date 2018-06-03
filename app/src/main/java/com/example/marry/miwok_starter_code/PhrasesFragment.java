package com.example.marry.miwok_starter_code;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {

    //cream un obiect al clasei MediaPlayer
    private MediaPlayer mMediaPlayer;

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //abandon th audio focus
            releaseAudioFocusForMyApp(getActivity());
        }
    }

    /**
     * create an instance of OnAudioFocusChangeListener, so we can know when the audio focus changes
     */
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                //if we gain audio focus we play the sound
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                //if we lose audio focus for a short period of time we just pause the play sound
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mMediaPlayer != null) {
                        //we pause the sound
                        mMediaPlayer.pause();
                        //and we need to tell the MediaPlayer, from where we want the sound to be played,
                        //if from the begining or from the moment we lose focus
                        //in this case we want from the begining
                        mMediaPlayer.seekTo(0);
                    }
                    break;
                //if we lose the audio focus to be played in the backgroud
                // we pause, because otherwise the sound will not be played properly
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (mMediaPlayer != null) {
                        //we pause the sound
                        mMediaPlayer.pause();
                        //and we need to tell the MediaPlayer, from where we want the sound to be played,
                        //if from the begining or from the moment we lose focus
                        //in this case we want from the begining
                        mMediaPlayer.seekTo(0);
                    }
                    break;
                //if we lose audio focus we stop the sound and we realease resources
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
            }
        }
    };

    //when we stop the view
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * A method to request audio focus for
     *
     * @param context will receive the class we want to gain the focus
     * @return if we gained or not the audio focus
     */
    private boolean requestAudioFocusForMyApp(final Context context) {
        //create an instance of the AudioManager - asa se creaza un obiect al clasei
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        /*
          create an int  variable to request the audio focus for playing the words
          @param mOnAudioFocusChangeListener the listener to be notified of audio focus changes
         * @param AudioManager.STREAM_MUSIC - Used to identify the volume of audio streams for music playback
         * @param  AudioManager.AUDIOFOCUS_GAIN_TRANSIENT - used to indicate a temporary gain or request of audio focus, anticipated to last a short amount of time
         */
        int result = am.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        /*
          if we gained the audio focus, the method will return true, else will return false
         */
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method to release audio focus for
     *
     * @param context will receive the class we want to gain the focus
     */
    private void releaseAudioFocusForMyApp(final Context context) {
        //create an instance of the class AudioManager
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //abandon the audioFocus
        am.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //cream un ArrayList cu elemente de tip Word - este clasa creata de noi care are 2 TextView-uri
        final ArrayList<Word> phrases = new ArrayList<Word>();

        phrases.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(getActivity(), phrases, R.color.category_phrases);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = phrases.get(position);
                releaseMediaPlayer();

                //if we have the audio focus
                //we create an Media Player and we play the sound
                boolean gotFocus = requestAudioFocusForMyApp(getActivity());
                if (gotFocus) {
                    mMediaPlayer = mMediaPlayer.create(getActivity(), word.getmMiwokAudio());
                    mMediaPlayer.start();
                }

                /*
                  This listener gets triggered when the {@link MediaPlayer} has completed
                  playing the audio file.
                 */
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaPlayer();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}

