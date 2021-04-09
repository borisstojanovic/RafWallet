package com.example.rafwalletproject.view.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        initView(view);
        initObservers(view);
    }

    private void initView(View view) {
        view.findViewById(R.id.txtAddD).setOnClickListener(v -> {
            sharedFinancesViewModel.addFinance("text", "txt", 100, false);
        });
    }

    private void initObservers(View view) {
        // Kada korisnik unese tekst i potvrdi unos, zelimo da prikazemo taj unos
        sharedFinancesViewModel.getIncomeSum().observe(getViewLifecycleOwner(), (income) -> {
            TextView txtIncome = view.findViewById(R.id.txtIncome);
            txtIncome.setText(income.toString());
        });
        sharedFinancesViewModel.getExpensesSum().observe(getViewLifecycleOwner(), (expense) -> {
            TextView txtExpense = view.findViewById(R.id.txtExpense);
            sharedFinancesViewModel.getExpensesSum().getValue();
            txtExpense.setText(expense.toString());
        });
        sharedFinancesViewModel.getTotalSum().observe(getViewLifecycleOwner(), (total) -> {
            TextView txtTotalDifference = view.findViewById(R.id.txtTotalDifference);
            txtTotalDifference.setText(total.toString());
            if(total >= 0) {
                txtTotalDifference.setTextColor(Color.GREEN);
            }else{
                txtTotalDifference.setTextColor(Color.RED);
            }
        });
    }

}