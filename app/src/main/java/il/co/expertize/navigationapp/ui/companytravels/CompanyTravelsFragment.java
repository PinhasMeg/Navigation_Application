package il.co.expertize.navigationapp.ui.companytravels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import il.co.expertize.navigationapp.Adapters.CustomListAdapter;
import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;
import il.co.expertize.navigationapp.ui.MainViewModel;

public class CompanyTravelsFragment extends Fragment {

    private CompanyTravelsViewModel companyTravelsViewModel;
    ListView itemsListView;
    MainViewModel mViewModel;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        companyTravelsViewModel = new ViewModelProvider(this).get(CompanyTravelsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_companytravels, container, false);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListView  = (ListView)view.findViewById(R.id.list_view_items_companytravels);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);

                //create adapter object
                CustomListAdapter adapter = new CustomListAdapter(context, tmp);


                adapter.setListener(new CustomListAdapter.CompanyTravelListener() {
                    @Override
                    public void onButtonClicked(int position, View view) {
                        if (view.getId() == R.id.bt_call) {
                            String phone = tmp.get(position).getClientPhone();

                            if (phone.isEmpty()) {
                                Toast.makeText(getContext(), "no phone number exist", Toast.LENGTH_LONG).show();
                            } else {

                            }
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + phone));
                            startActivity(callIntent);
                        }
                        if (view.getId() == R.id.bt_update) {
                            mViewModel.updateTravel(tmp.get(position));
                        }
                    }
                });

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }

}