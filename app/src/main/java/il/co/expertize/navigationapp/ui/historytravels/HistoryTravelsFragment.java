package il.co.expertize.navigationapp.ui.historytravels;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import il.co.expertize.navigationapp.R;
import il.co.expertize.navigationapp.ui.MainViewModel;

public class HistoryTravelsFragment extends Fragment {

    private HistoryTravelsViewModel historyTravelsViewModel;
    ListView itemsListView;
    MainViewModel mViewModel;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        historyTravelsViewModel = new ViewModelProvider(this).get(HistoryTravelsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historytravels, container, false);

        return root;
    }
}