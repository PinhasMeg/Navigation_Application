package il.co.expertize.navigationapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import il.co.expertize.navigationapp.Adapters.CustomListAdapterHistoryTravels;
import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;
import il.co.expertize.navigationapp.ui.MainViewModel;

public class HistoryTravelsFragment extends Fragment {

    ListView itemsListView;
    MainViewModel mViewModel;
    Context context;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historytravels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListView  = (ListView)view.findViewById(R.id.list_view_items_historytravels);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.addRemoveTravel();
        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> travelArrayList = new ArrayList<Travel>(travels);

                //create adapter object
                CustomListAdapterHistoryTravels adapter = new CustomListAdapterHistoryTravels(context, travelArrayList);


                adapter.setListener(new CustomListAdapterHistoryTravels.CompanyTravelListener() {
                    @Override
                    public void onButtonClicked(int position, View view) {
                        if (view.getId() == R.id.send_mail_society) {
                            String mail = travelArrayList.get(position).getClientEmail();
                            if (mail.isEmpty()) {
                                Toast.makeText(getContext(), "no mail address exist", Toast.LENGTH_LONG).show();
                            } else {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{travelArrayList.get(position).getClientEmail() }); //todo change email to society email
                                email.putExtra(Intent.EXTRA_SUBJECT, "roull");
                                email.putExtra(Intent.EXTRA_TEXT, "rouli roulou");
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }
                        }
                        if (view.getId() == R.id.validate_after_payment) {
                            //mViewModel.updateTravel(travelArrayList.get(position));
                        }
                    }
                });

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }

}