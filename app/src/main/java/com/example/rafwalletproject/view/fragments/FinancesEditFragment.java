package com.example.rafwalletproject.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;

public class FinancesEditFragment extends Fragment {
    private FinancesViewModel financesViewModel;

    public FinancesEditFragment() {
        super(R.layout.fragment_finances_edit);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        financesViewModel = new ViewModelProvider(requireActivity()).get(FinancesViewModel.class);
        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
        initObservers(view);
    }

    private void initListeners(View view) {
        EditText txtTitle = view.findViewById(R.id.edtFinanceTitle);
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                financesViewModel.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText txtQuantity = view.findViewById(R.id.edtFinanceQuantity);
        txtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int quantity = 0;
                if(s.length() > 0){
                    quantity = Integer.parseInt(s.toString());
                }
                financesViewModel.setQuantity(quantity);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void initView(View view) {
        EditText txtTitle = view.findViewById(R.id.edtFinanceTitle);
        txtTitle.setText(financesViewModel.getTitle().getValue());
        EditText txtQuantity = view.findViewById(R.id.edtFinanceQuantity);
        txtQuantity.setText(String.valueOf(financesViewModel.getQuantity().getValue()));
    }

    private void initObservers(View view) {

    }
}
