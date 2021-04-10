package com.example.rafwalletproject.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.models.Finances;
import com.example.rafwalletproject.view.fragments.AudioPlayerFragment;
import com.example.rafwalletproject.view.fragments.FinancesDescriptionFragment;
import com.example.rafwalletproject.view.fragments.FinancesDescriptionTextFragment;
import com.example.rafwalletproject.view.fragments.FinancesDetailsDescriptionFragment;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;

public class DetailsFinancesActivity extends AppCompatActivity {

    private FinancesViewModel financesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        financesViewModel = new ViewModelProvider(this).get(FinancesViewModel.class);
        Finances finances = (Finances) getIntent().getExtras().get("finance");
        if(finances != null){
            financesViewModel.initFinances(finances);
        }else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            finish();
        }
        setContentView(R.layout.activity_details_finances);
        init();
    }

    private void init(){
        initView();
    }

    private void initView() {
        if (financesViewModel.getIsAudio().getValue() != null && financesViewModel.getIsAudio().getValue()){
            initAudioFragment();
        }else {
            initTextFragment();
        }
    }

    private void initAudioFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        transaction.replace(R.id.detailsFcv, new AudioPlayerFragment());
        transaction.commit();
    }

    private void initTextFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        transaction.replace(R.id.detailsFcv, new FinancesDetailsDescriptionFragment());
        transaction.commit();
    }

    private FragmentTransaction createTransactionWithAnimation() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return transaction;
    }
}