package il.co.expertize.navigationapp.ui.registeredtravels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import il.co.expertize.navigationapp.R;

public class RegisteredTravelsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisteredTravelsViewModel registeredTravelsViewModel = new ViewModelProvider(this).get(RegisteredTravelsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registeredtravels, container, false);
        final TextView textView = root.findViewById(R.id.text_registeredtravels);

        registeredTravelsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            textView.setText(s);
        });

        return root;
    }
}