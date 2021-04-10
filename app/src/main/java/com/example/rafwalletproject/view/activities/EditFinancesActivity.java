package com.example.rafwalletproject.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.SharedFinancesViewModel;

import org.jetbrains.annotations.NotNull;

public class EditFinancesActivity extends AppCompatActivity {
    private SharedFinancesViewModel sharedFinancesViewModel;

    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_finances);
        if(hasPermissions(this, PERMISSIONS)) {
            init();
        }else {
            // Ukoliko nije, trazimo ih
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void init(){

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
                    // Ukoliko nema odbijenih dozvola, nastavljamo dalje
                    init();
                } else {
                    Toast.makeText(this, "Missing permissions! " + permissionsDenied.toString(), Toast.LENGTH_LONG).show();
                    // Ukoliko ima odbijenih dozvola ispisujemo poruku i zatvaramo activity
                    finish();
                }
            }
        }
    }
}