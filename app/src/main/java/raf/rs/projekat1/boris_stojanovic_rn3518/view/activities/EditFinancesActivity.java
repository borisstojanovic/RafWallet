package raf.rs.projekat1.boris_stojanovic_rn3518.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.models.Finances;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments.FinancesDescriptionFragment;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments.FinancesDescriptionTextFragment;
import raf.rs.projekat1.boris_stojanovic_rn3518.viewmodels.FinancesViewModel;

import org.jetbrains.annotations.NotNull;

public class EditFinancesActivity extends AppCompatActivity {

    private FinancesViewModel financesViewModel;

    //View
    private Button btnConfirm, btnCancel;
    private CheckBox checkBoxAudio;


    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        financesViewModel = new ViewModelProvider(this).get(FinancesViewModel.class);
        Finances finances = (Finances) getIntent().getExtras().get("finance");
        if(finances != null){
            financesViewModel.initFinances(finances);
        }
        setContentView(R.layout.activity_edit_finances);
        init();
    }

    private void init(){
        initView();
        initListeners();
    }

    private void initView() {
        btnConfirm = findViewById(R.id.btnConfirmEditFinance);
        btnCancel = findViewById(R.id.btnCancelEditFinance);
        checkBoxAudio = findViewById(R.id.checkBoxAudio);
        if (financesViewModel.getIsAudio().getValue() != null && financesViewModel.getIsAudio().getValue()){
            checkBoxAudio.setChecked(true);
            initAudioFragment();
        }else {
            initTextFragment();
        }
    }

    private void initListeners() {
        btnCancel.setOnClickListener( v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        btnConfirm.setOnClickListener( v -> {
            Intent intent = new Intent();
            if(financesViewModel.getId().getValue() == null){
                Toast.makeText(this, "Id is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }else if(financesViewModel.getTitle().getValue() == null || financesViewModel.getTitle().getValue().isEmpty()){
                Toast.makeText(this, "Title is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }else if(financesViewModel.getQuantity().getValue() == null || financesViewModel.getQuantity().getValue() == 0){
                Toast.makeText(this, "Quantity is mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            Finances finances = new Finances(financesViewModel.getId().getValue(), financesViewModel.getDescription().getValue(),
                    financesViewModel.getIsIncome().getValue(), financesViewModel.getIsAudio().getValue(),
                    financesViewModel.getTitle().getValue(), financesViewModel.getQuantity().getValue());
            intent.putExtra("finance", finances);
            setResult(RESULT_OK, intent);
            finish();
        });
        checkBoxAudio.setOnClickListener( v -> {
            if (checkBoxAudio.isChecked()){
                if(hasPermissions(this, PERMISSIONS)) {
                    initAudioFragment();
                }else {
                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                }
            }else{
                initTextFragment();
            }
        });
    }

    private void initAudioFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        transaction.replace(R.id.descriptionFcv, new FinancesDescriptionFragment());
        transaction.commit();
    }

    private void initTextFragment(){
        FragmentTransaction transaction = createTransactionWithAnimation();
        transaction.replace(R.id.descriptionFcv, new FinancesDescriptionTextFragment());
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
                    Toast.makeText(this, "Missing permissions! " + permissionsDenied.toString(), Toast.LENGTH_LONG).show();
                    checkBoxAudio.setChecked(false);
                    initTextFragment();
                }
            }
        }
    }

    private FragmentTransaction createTransactionWithAnimation() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return transaction;
    }
}