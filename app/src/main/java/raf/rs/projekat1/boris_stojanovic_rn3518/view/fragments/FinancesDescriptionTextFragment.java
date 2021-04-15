package raf.rs.projekat1.boris_stojanovic_rn3518.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import raf.rs.projekat1.boris_stojanovic_rn3518.R;
import raf.rs.projekat1.boris_stojanovic_rn3518.viewmodels.FinancesViewModel;

import timber.log.Timber;

public class FinancesDescriptionTextFragment extends Fragment {

    private FinancesViewModel financesViewModel;

    public FinancesDescriptionTextFragment() {
        super(R.layout.fragment_finances_text_description);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null && getArguments().get("isFragment") != null) {
            financesViewModel = new ViewModelProvider(requireParentFragment()).get(FinancesViewModel.class);
        }else{
            financesViewModel = new ViewModelProvider(requireActivity()).get(FinancesViewModel.class);
        }
        init(view);
    }

    private void init(View view){
        initView(view);
        initListeners(view);
    }

    private void initListeners(View view) {
        EditText edtDescription = view.findViewById(R.id.edtFinanceDescription);
        edtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                financesViewModel.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                financesViewModel.setIsAudio(false);
                Timber.d("here");
            }
        });
    }

    private void initView(View view) {
        EditText edtDescription = view.findViewById(R.id.edtFinanceDescription);
        if(financesViewModel.getIsAudio().getValue() != null && !financesViewModel.getIsAudio().getValue()) {
            edtDescription.setText(financesViewModel.getDescription().getValue());
        }
    }


}
