package raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.viewmodels.FinancesViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinancesDescriptionFragment extends Fragment {
    private FinancesViewModel financesViewModel;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
        }
    }


    private void init(View view) {
        if(getArguments() != null && getArguments().get("isFragment") != null) {
            financesViewModel = new ViewModelProvider(requireParentFragment()).get(FinancesViewModel.class);
        }else{
            financesViewModel = new ViewModelProvider(requireActivity()).get(FinancesViewModel.class);
        }
        File folder = new File(requireActivity().getFilesDir(), "sounds");
        if(!folder.exists()) folder.mkdir();
        file = new File(folder, new SimpleDateFormat("ddMMyy-hhmmss.SSS").format(new Date()).toString() + "record.3gp");
        initListeners(view);
    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
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
            mediaRecorder.stop();
            mediaRecorder.release();
            financesViewModel.setDescription(file.getPath());
            financesViewModel.setIsAudio(true);
            mediaRecorder = null;
            ((Chronometer)view.findViewById(R.id.chronometer)).setBase(SystemClock.elapsedRealtime());
            ((Chronometer)view.findViewById(R.id.chronometer)).stop();
        });
    }
}
