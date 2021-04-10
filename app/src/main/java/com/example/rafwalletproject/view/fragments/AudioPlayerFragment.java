package com.example.rafwalletproject.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;
import com.example.rafwalletproject.viewmodels.SharedFinancesViewModel;

import java.io.File;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class AudioPlayerFragment extends Fragment {
    private FinancesViewModel financesViewModel;

    // Variables

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    private AudioFocusRequest audioFocusRequest;
    private Handler handler = new Handler();
    private Runnable runnable;

    public AudioPlayerFragment() {
        super(R.layout.fragment_audio_player);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        financesViewModel = new ViewModelProvider(requireActivity()).get(FinancesViewModel.class);
        init(view);
    }

    private void init(View view) {
        initPlayer(view);
        initRunnable(view);
        initListeners(view);
    }


    private void initPlayer(View view) {
        // Initialize media player
        if(financesViewModel.getDescription().getValue() != null)
            mediaPlayer = MediaPlayer.create(requireActivity(), Uri.fromFile(new File(financesViewModel.getDescription().getValue())));

        Timber.d(Uri.fromFile(new File(financesViewModel.getDescription().getValue())).toString());
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Get duration of media player
        int duration = mediaPlayer.getDuration();
        // Convert millisecond to minute and second
        String sDuration = convertFormat(duration);
        // Set duration on text view
        ((TextView)view.findViewById(R.id.player_duration)).setText(sDuration);
    }

    private void initRunnable(View view) {
        // Initialize runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                // Set progress on seek bar
                ((SeekBar)view.findViewById(R.id.seek_bar)).setProgress(mediaPlayer.getCurrentPosition());
                // Handler post delay for 0.5 second
                handler.postDelayed(this, 500);
            }
        };
    }

    private void initListeners(View view) {
        ((ImageView)view.findViewById(R.id.bt_play)).setOnClickListener(v -> play(view));

        ((ImageView)view.findViewById(R.id.bt_pause)).setOnClickListener(v -> pause(view));

        ((ImageView)view.findViewById(R.id.bt_fw)).setOnClickListener(v -> {
            // Get current position of media player
            int currentPosition = mediaPlayer.getCurrentPosition();
            // Get duration of media player
            int durationFw = mediaPlayer.getDuration();
            // Check condition
            if (mediaPlayer.isPlaying() && durationFw != currentPosition) {
                // When media is playing and duration is not equal to current position fast forward for 5 seconds
                currentPosition += 5000;
                // Set current position on text view
                ((TextView)view.findViewById(R.id.player_position)).setText(convertFormat(currentPosition));
                // Set progress on seek bar
                mediaPlayer.seekTo(currentPosition);
            }
        });

        ((ImageView)view.findViewById(R.id.bt_rew)).setOnClickListener(v -> {
            // Get current position of media player
            int currentPosition = mediaPlayer.getCurrentPosition();
            // Check condition
            if (mediaPlayer.isPlaying() && currentPosition > 5000) {
                // When media is playing and current position is greater than 5 seconds rewind for 5 seconds
                currentPosition -= 5000;
                // Set current position on text view
                ((TextView)view.findViewById(R.id.player_position)).setText(convertFormat(currentPosition));
                // Set progress on seek bar
                mediaPlayer.seekTo(currentPosition);
            }
        });

        ((SeekBar)view.findViewById(R.id.seek_bar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Check condition
                if (fromUser) {
                    // When drag the seek bar set progress on seek bar
                    mediaPlayer.seekTo(progress);
                }
                // Set current position on text view
                ((TextView)view.findViewById(R.id.player_position)).setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // We have to handle focus changes
        onAudioFocusChangeListener = focusChange -> {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS: {
                    // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                    // The AUDIOFOCUS_LOSS case means we've lost audio focus
                    Timber.e("AUDIOFOCUS_LOSS_TRANSIENT or AUDIOFOCUS_LOSS");
                    pause(view);
                } break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                    // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                    // our app is allowed to continue playing sound but at a lower volume.
                    Timber.e("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    playerDuck(true);
                } break;
                case AudioManager.AUDIOFOCUS_GAIN: {
                    // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                    Timber.e("AUDIOFOCUS_GAIN");
                    playerDuck(false);
                    play(view);
                } break;
            }
        };

        mediaPlayer.setOnCompletionListener(mp -> {
            // Hide pause button
            ((ImageView)view.findViewById(R.id.bt_pause)).setVisibility(View.GONE);
            // Show play button
            ((ImageView)view.findViewById(R.id.bt_play)).setVisibility(View.VISIBLE);
            // Set media player to initial position
            mediaPlayer.seekTo(0);
        });

        // Description of the audioFocusRequest
        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                .build();
    }

    private void pause(View view) {
        // Hide pause button
        ((ImageView)view.findViewById(R.id.bt_pause)).setVisibility(View.GONE);
        // Show play button
        ((ImageView)view.findViewById(R.id.bt_play)).setVisibility(View.VISIBLE);
        // Pause media player
        mediaPlayer.pause();
        // Stop handler
        handler.removeCallbacks(runnable);
    }

    private void play(View view) {
        // Request audio focus
        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Timber.e("AUDIOFOCUS_REQUEST_GRANTED");
            // Hide play button
            ((ImageView)view.findViewById(R.id.bt_play)).setVisibility(View.GONE);
            // Show pause button
            ((ImageView)view.findViewById(R.id.bt_pause)).setVisibility(View.VISIBLE);
            // Start media player
            mediaPlayer.start();
            // Set max on seek bar
            ((SeekBar)view.findViewById(R.id.seek_bar)).setMax(mediaPlayer.getDuration());
            // Start handler
            handler.postDelayed(runnable, 0);
        }
    }

    private void releaseMediaPlayer() {
        // Release resources
        if(mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        audioManager.abandonAudioFocusRequest(audioFocusRequest);
        // Remove and stop running threads
        handler.removeCallbacks(runnable);
        handler.removeCallbacksAndMessages(null);
        runnable = null;
        handler = null;
    }

    public synchronized void playerDuck(boolean duck) {
        if (mediaPlayer != null) {
            // Reduce the volume when ducking - otherwise play at full volume.
            mediaPlayer.setVolume(duck ? 0.2f : 1.0f, duck ? 0.2f : 1.0f);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Proverite sta se desava sa zvukom kada sklonite komentar i izadjete iz aplikacije sa home button
        //pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
