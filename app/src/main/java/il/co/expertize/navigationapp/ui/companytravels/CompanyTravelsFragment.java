package il.co.expertize.navigationapp.ui.companytravels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import il.co.expertize.navigationapp.R;

public class CompanyTravelsFragment extends Fragment {

    private CompanyTravelsViewModel companyTravelsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        companyTravelsViewModel =
                new ViewModelProvider(this).get(CompanyTravelsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_companytravels, container, false);
        final TextView textView = root.findViewById(R.id.list_view_items_companytravels);
        companyTravelsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}