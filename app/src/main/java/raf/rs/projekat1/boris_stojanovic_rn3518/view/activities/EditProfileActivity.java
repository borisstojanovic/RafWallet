package raf.rs.projekat1.boris_stojanovic_rn3518.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;

public class EditProfileActivity extends AppCompatActivity {

    //Code
    private static String PREFERENCES_NAME = "loginPreferences";

    //Views
    EditText edtEditFirstName, edtEditBank, edtEditLastName;
    Button btnConfirm, btnCancel;
    TextView txtEditInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    private void init(){
        initView();
        initListeners();
    }

    private boolean doEdit(){
        String bank = edtEditBank.getText().toString();
        String firstName = edtEditFirstName.getText().toString();
        String lastName = edtEditLastName.getText().toString();
        if(bank.isEmpty() || firstName.isEmpty() || lastName.isEmpty()){
            txtEditInfo.setText("All fields are mandatory");
            return false;
        }
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString("firstName", firstName).apply();
        sharedPreferences.edit().putString("lastName", lastName).apply();
        sharedPreferences.edit().putString("bank", bank).apply();
        return true;
    }

    private void initListeners() {
        btnConfirm.setOnClickListener(v -> {
            if(doEdit()) {
                Toast.makeText(this, "Edit Successful", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void initView() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        edtEditFirstName = findViewById(R.id.edtEditFirstName);
        edtEditLastName = findViewById(R.id.edtEditLastName);
        edtEditBank = findViewById(R.id.edtEditBank);
        edtEditFirstName.setText(sharedPreferences.getString("firstName", ""));
        edtEditLastName.setText(sharedPreferences.getString("lastName", ""));
        edtEditBank.setText(sharedPreferences.getString("bank", ""));
        btnConfirm = findViewById(R.id.btnConfirmEdit);
        btnCancel = findViewById(R.id.btnCancelEdit);
        txtEditInfo = findViewById(R.id.txtEditInfo);
    }
}