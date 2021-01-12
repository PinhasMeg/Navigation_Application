package il.co.expertize.navigationapp.ui.historytravels;

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

public class HistoryTravelsFragment extends Fragment {

    private HistoryTravelsViewModel historyTravelsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyTravelsViewModel =
                new ViewModelProvider(this).get(HistoryTravelsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historytravels, container, false);
        final TextView textView = root.findViewById(R.id.text_historytravels);
        historyTravelsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}