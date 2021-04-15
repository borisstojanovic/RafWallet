package raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.activities.EditProfileActivity;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.activities.SplashScreenActivity;

import timber.log.Timber;

public class UserProfileFragment extends Fragment {

    //Code
    private static String PREFERENCES_NAME = "loginPreferences";

    public UserProfileFragment(){
        super(R.layout.fragment_user_profile);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.e("ON CREATE VIEW");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        Timber.e("ON VIEW CREATED");
    }

    private ActivityResultLauncher<Intent> editActivityResultLauncher;

    private void init(View view) {
        initViews(view);
        initListeners(view);
        editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            onResult();
                        }else if(result.getResultCode() == Activity.RESULT_CANCELED){
                            Toast.makeText(getActivity(), "Edit Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initViews(View view) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        ((TextView)view.findViewById(R.id.txtFirstName)).setText(sharedPreferences.getString("firstName", ""));
        ((TextView)view.findViewById(R.id.txtLastName)).setText(sharedPreferences.getString("lastName", ""));
        ((TextView)view.findViewById(R.id.txtBank)).setText(sharedPreferences.getString("bank", ""));
    }

    private void doLogout(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void onResult(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setReorderingAllowed(false);
        ft.detach(this);
        ft.commit();
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.attach(this);
        ft.commit();
    }


    private void initListeners(View view) {
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            doLogout();
            Intent i = new Intent(getActivity(), SplashScreenActivity.class);
            startActivity(i);
            getActivity().finish();
            Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_LONG).show();
        });

        view.findViewById(R.id.btnEdit).setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), EditProfileActivity.class);
            editActivityResultLauncher.launch(i);
        });
    }
}