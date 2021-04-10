package com.example.rafwalletproject.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwalletproject.R;
import com.example.rafwalletproject.viewmodels.FinancesViewModel;

public class FinancesDetailsDescriptionFragment extends Fragment {

    private FinancesViewModel financesViewModel;

    public FinancesDetailsDescriptionFragment(){ super(R.layout.fragment_finances_details_description); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        financesViewModel = new ViewModelProvider(requireActivity()).get(FinancesViewModel.class);
        init(view);
    }

    private void init(View view){
        initView(view);
    }

    private void initView(View view) {
        TextView description = view.findViewById(R.id.txtDetailsDescription);
        description.setText(financesViewModel.getDescription().getValue());
    }

}
