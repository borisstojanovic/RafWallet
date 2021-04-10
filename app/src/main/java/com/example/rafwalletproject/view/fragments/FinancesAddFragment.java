package com.example.rafwalletproject.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;
import com.example.rafwalletproject.viewmodels.SharedFinancesViewModel;

import org.jetbrains.annotations.NotNull;

public class FinancesAddFragment extends Fragment {
    private FinancesViewModel financesViewModel;

    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private SharedFinancesViewModel sharedFinancesViewModel;

    public FinancesAddFragment() { super(R.layout.fragment_finances_add); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        financesViewModel = new ViewModelProvider(this).get(FinancesViewModel.class);
        sharedFinancesViewModel = new ViewModelProvider(requireActivity()).get(SharedFinancesViewModel.class);
        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        CheckBox checkBox = view.findViewById(R.id.checkBoxAddAudio);
        initInputFragment();
        if (checkBox.isChecked()){
            if(hasPermissions(requireActivity(), PERMISSIONS)) {
                financesViewModel.setIsAudio(true);
                initAudioFragment();
            }else {
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
            }
        }else {
            financesViewModel.setIsAudio(false);
            initTextFragment();
        }
    }

    private void initListeners(View view) {
        view.findViewById(R.id.btnConfirmAddFinance).setOnClickListener( v -> {
            if(financesViewModel.getTitle().getValue() == null || financesViewModel.getTitle().getValue().isEmpty()){
                Toast.makeText(requireActivity(), "Title is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }else if(financesViewModel.getQuantity().getValue() == null || financesViewModel.getQuantity().getValue() == 0){
                Toast.makeText(requireActivity(), "Quantity is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            sharedFinancesViewModel.addFinance(financesViewModel.getTitle().getValue(), financesViewModel.getDescription().getValue(),
                    financesViewModel.getQuantity().getValue(), financesViewModel.getIsIncome().getValue(), financesViewModel.getIsAudio().getValue());
            Toast.makeText(requireActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
            resetView(view);
        });
        CheckBox checkBoxAddAudio = view.findViewById(R.id.checkBoxAddAudio);
        checkBoxAddAudio.setOnClickListener( v -> {
            if (checkBoxAddAudio.isChecked()){
                if(hasPermissions(requireActivity(), PERMISSIONS)) {
                    initAudioFragment();
                }else {
                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                }
            }else{
                initTextFragment();
            }
        });
    }

    private void resetView(View view){
        CheckBox checkBox = view.findViewById(R.id.checkBoxAddAudio);
        checkBox.setChecked(false);
        getViewModelStore().clear();
        financesViewModel = new ViewModelProvider(this).get(FinancesViewModel.class);
        financesViewModel.setIsAudio(false);
        initTextFragment();
        initInputFragment();
    }

    private void initAudioFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        Fragment fragment = new FinancesDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFragment", true);
        fragment.setArguments(bundle);
        transaction.replace(R.id.descriptionAddFcv, fragment);
        transaction.commit();
    }

    private void initTextFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        Fragment fragment = new FinancesDescriptionTextFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFragment", true);
        fragment.setArguments(bundle);
        transaction.replace(R.id.descriptionAddFcv, fragment);
        transaction.commit();
    }

    private void initInputFragment(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.inputAddFcv, new FinancesAddInputFragment());
        transaction.commit();
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String @NotNull [] permissionsList, int @NotNull [] grantResults) {
        // Ovde dobijamo odgovor na requestPermissions
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0) {
                StringBuilder permissionsDenied = new StringBuilder();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permissionsDenied.append("\n").append(permissionsList[i]);
                    }
                }
                if (permissionsDenied.toString().length() == 0) {
                    initAudioFragment();
                } else {
                    Toast.makeText(requireActivity(), "Missing permissions! " + permissionsDenied.toString(), Toast.LENGTH_LONG).show();
                    ((CheckBox)getView().findViewById(R.id.checkBoxAddAudio)).setChecked(false);
                    initTextFragment();
                }
            }
        }
    }

    private FragmentTransaction createTransactionWithAnimation() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return transaction;
    }
}
