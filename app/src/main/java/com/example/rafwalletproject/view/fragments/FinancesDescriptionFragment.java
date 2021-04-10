package com.example.rafwalletproject.view.fragments;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rafwalletproject.R;

import java.io.File;
import java.io.IOException;

public class FinancesDescriptionFragment extends Fragment {
    private MediaRecorder mediaRecorder;
    public static File file;

    public FinancesDescriptionFragment() {
        super(R.layout.fragment_finances_description);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }


    private void init(View view) {
        File folder = new File(requireActivity().getFilesDir(), "sounds");
        if(!folder.exists()) folder.mkdir();
        file = new File(folder, "record.3gp");
        initListeners(view);
    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        // Postavljanje parametara za mediaRecorder
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);
    }

    private void initListeners(View view) {
        ImageView btnMic = view.findViewById(R.id.btnMic);
        ImageView btnRecording = view.findViewById(R.id.btnRecording);
        btnMic.setOnClickListener(v -> {
            try {
                btnMic.setVisibility(View.GONE);
                btnRecording.setVisibility(View.VISIBLE);
                initMediaRecorder(file);
                // Pokrecemo snimanje
                mediaRecorder.prepare();
                mediaRecorder.start();
                ((Chronometer)view.findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime());
                ((Chronometer)view.findViewById(R.id.chronometer)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRecording.setOnClickListener(v -> {
            btnMic.setVisibility(View.VISIBLE);
            btnRecording.setVisibility(View.GONE);
            // Zaustavljamo snimanje i oslobadjamo resurse
            // Metodom stop() se snimljeni resurs cuva u fajlu koji smo prosledili pri inicijalizaciji mediaRecorder-a
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            ((Chronometer)view.findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime());
            ((Chronometer)view.findViewById(R.id.chronometer)).stop();
        });
    }
}
