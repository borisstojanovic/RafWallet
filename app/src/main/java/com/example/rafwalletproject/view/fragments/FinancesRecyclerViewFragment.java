package com.example.rafwalletproject.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.view.recyclerview.FinancesAdapter;
import com.example.rafwalletproject.view.recyclerview.FinancesDiffItemCallback;
import com.example.rafwalletproject.viewmodels.SharedFinancesViewModel;

import timber.log.Timber;

public class FinancesRecyclerViewFragment extends Fragment {
    private SharedFinancesViewModel sharedFinancesViewModel;

    private boolean isIncome = false;

    private FinancesAdapter financesAdapter;

    public FinancesRecyclerViewFragment() {
        super(R.layout.fragment_recycler);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.e("ON CREATE VIEW");
        if(getArguments().get("isIncome") != null) {
            isIncome = (boolean) getArguments().get("isIncome");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedFinancesViewModel = new ViewModelProvider(requireActivity()).get(SharedFinancesViewModel.class);
        init(view);
    }

    private void init(View view) {
        initObservers(view);
        initRecycler(view);
    }

    private void initObservers(View view) {
        if(isIncome){
            sharedFinancesViewModel.getIncomes().observe(getViewLifecycleOwner(), finances -> {
                financesAdapter.submitList(finances);
            });
        }else{
            sharedFinancesViewModel.getExpenses().observe(getViewLifecycleOwner(), finances -> {
                financesAdapter.submitList(finances);
            });
        }
    }

    private void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.listRv);
        financesAdapter = new FinancesAdapter(new FinancesDiffItemCallback(), finances -> {
            Toast.makeText(requireContext(), finances.getId()+"", Toast.LENGTH_SHORT).show();
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(financesAdapter);
    }
}