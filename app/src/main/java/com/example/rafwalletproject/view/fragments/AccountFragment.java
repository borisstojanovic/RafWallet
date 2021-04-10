package com.example.rafwalletproject.view.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.SharedFinancesViewModel;

public class AccountFragment extends Fragment {

    private SharedFinancesViewModel sharedFinancesViewModel;

    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedFinancesViewModel = new ViewModelProvider(requireActivity()).get(SharedFinancesViewModel.class);
        init(view);
    }

    private void init(View view){
        initObservers(view);
    }

    private void initObservers(View view) {
        sharedFinancesViewModel.getIncomeSum().observe(getViewLifecycleOwner(), (income) -> {
            TextView txtIncome = view.findViewById(R.id.txtIncome);
            txtIncome.setText(String.valueOf(income));
        });
        sharedFinancesViewModel.getExpensesSum().observe(getViewLifecycleOwner(), (expense) -> {
            TextView txtExpense = view.findViewById(R.id.txtExpense);
            sharedFinancesViewModel.getExpensesSum().getValue();
            txtExpense.setText(String.valueOf(expense));
        });
        sharedFinancesViewModel.getTotalSum().observe(getViewLifecycleOwner(), (total) -> {
            TextView txtTotalDifference = view.findViewById(R.id.txtTotalDifference);
            txtTotalDifference.setText(String.valueOf(total));
            if(total >= 0) {
                txtTotalDifference.setTextColor(Color.GREEN);
            }else{
                txtTotalDifference.setTextColor(Color.RED);
            }
        });
    }

}