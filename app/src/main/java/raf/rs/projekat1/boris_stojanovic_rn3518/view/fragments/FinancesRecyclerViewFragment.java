package raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.models.Finances;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.activities.DetailsFinancesActivity;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.activities.EditFinancesActivity;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.recyclerview.FinancesAdapter;
import raf.rs.projekat1.boris_stojanovic_rn3518.view.recyclerview.FinancesDiffItemCallback;
import raf.rs.projekat1.boris_stojanovic_rn3518.viewmodels.SharedFinancesViewModel;

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
        if(getArguments() != null && getArguments().get("isIncome") != null) {
            isIncome = (boolean) getArguments().get("isIncome");
        }else{
            isIncome = false;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedFinancesViewModel = new ViewModelProvider(requireActivity()).get(SharedFinancesViewModel.class);
        init(view);
    }

    private ActivityResultLauncher<Intent> editActivityResultLauncher;

    private void init(View view) {
        editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            onResult(result);
                        }else if(result.getResultCode() == Activity.RESULT_CANCELED){
                            Toast.makeText(getActivity(), "Edit Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.findViewById(R.id.listRv).getContext(),
                LinearLayout.VERTICAL);
        ((RecyclerView)view.findViewById(R.id.listRv)).addItemDecoration(dividerItemDecoration);

        initObservers(view);
        initRecycler(view);
    }

    private void onResult(ActivityResult result) {
        Finances finances = (Finances) result.getData().getExtras().get("finance");
        sharedFinancesViewModel.editFinance(finances);
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
            Intent intent = new Intent(requireActivity(), DetailsFinancesActivity.class);
            intent.putExtra("finance", finances);
            startActivity(intent);
            return null;
        }, finances -> {
            sharedFinancesViewModel.removeFinance(finances);
            return null;
        }, editFinances -> {
            Intent intent = new Intent(requireActivity(), EditFinancesActivity.class);
            intent.putExtra("finance", editFinances);
            editActivityResultLauncher.launch(intent);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(financesAdapter);
    }
}
