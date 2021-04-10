package com.example.rafwalletproject.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;

public class FinancesAddInputFragment extends Fragment {
    private FinancesViewModel financesViewModel;

    public FinancesAddInputFragment() {
        super(R.layout.fragment_finances_add_input);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        financesViewModel = new ViewModelProvider(requireParentFragment()).get(FinancesViewModel.class);
        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
    }

    private void initListeners(View view) {
        EditText txtTitle = view.findViewById(R.id.edtAddFinanceTitle);
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
        EditText txtQuantity = view.findViewById(R.id.edtAddFinanceQuantity);
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
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerStr = (String) spinner.getItemAtPosition(position);
                if(spinnerStr.toLowerCase().equals("income")){
                    financesViewModel.setIsIncome(true);
                }else{
                    financesViewModel.setIsIncome(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView(View view) {
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.finances, android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
    }
}
