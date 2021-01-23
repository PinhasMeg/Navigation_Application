package il.co.expertize.navigationapp.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import il.co.expertize.navigationapp.Adapters.CustomListAdapterRegisteredTravels;
import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;
import il.co.expertize.navigationapp.ui.MainViewModel;

public class RegisteredTravelsFragment extends Fragment {

    ListView itemsListView;
    MainViewModel mViewModel;
    Context context;
    String chosenSociety;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registeredtravels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListView  = (ListView)view.findViewById(R.id.list_view_items_registeredtravels);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.addRemoveTravel();
        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> travelArrayList;
                travelArrayList = mViewModel.checkTravelsByUser(travels);

                //create adapter object
                CustomListAdapterRegisteredTravels adapter = new CustomListAdapterRegisteredTravels(context, travelArrayList);
                adapter.setListener(new CustomListAdapterRegisteredTravels.CompanyTravelListener() {
                    @Override
                    public void onButtonClicked(int position, View view) {
                        if (view.getId() == R.id.accepted_travel) {
                            Travel currentTravel = travelArrayList.get(position);
                            HashMap<String, Boolean> company = currentTravel.getCompany();

                            if (currentTravel.getRequesType() != Travel.RequestType.sent)
                                Toast.makeText(getContext(), "You already hired " + getKeyByValue(company, true) + "!", Toast.LENGTH_LONG).show();
                            else {
                                if (!chosenSociety.equals("NO")) {
                                    company.replace(chosenSociety, true);
                                    currentTravel.setRequesType(Travel.RequestType.accepted);
                                    mViewModel.updateTravel(currentTravel);
                                    if(company.containsValue(true))
                                        Toast.makeText(getContext(), "Good job, you just hired " + getKeyByValue(company, true) + "!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        if (view.getId() == R.id.travel_start) {
                            Travel currentTravel = travelArrayList.get(position);
                            if (currentTravel.getRequesType() == Travel.RequestType.sent)
                                Toast.makeText(getContext(), "Please, first hire a company.", Toast.LENGTH_LONG).show();
                            else  if (currentTravel.getRequesType() != Travel.RequestType.accepted)
                                Toast.makeText(getContext(), "You already started the travel!", Toast.LENGTH_LONG).show();

                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Are you sure to start the travel now?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        currentTravel.setRequesType(Travel.RequestType.run);
                                        mViewModel.updateTravel(currentTravel);
                                        Toast.makeText(getContext(), "Good road!", Toast.LENGTH_LONG).show();
                                    }
                                });

                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                });

                                alert.show();
                            }
                        }
                        if (view.getId() == R.id.travel_finish) {
                            Travel currentTravel = travelArrayList.get(position);
                            if (currentTravel.getRequesType() == Travel.RequestType.close)
                                Toast.makeText(getContext(), "You already closed the travel!", Toast.LENGTH_LONG).show();
                            else if (currentTravel.getRequesType() != Travel.RequestType.run)
                                Toast.makeText(getContext(), "Please, first start the travel.", Toast.LENGTH_LONG).show();
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Did you finished the travel?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Travel currentTravel = travelArrayList.get(position);
                                        currentTravel.setRequesType(Travel.RequestType.close);
                                        mViewModel.updateTravel(currentTravel);
                                        Toast.makeText(getContext(), "Thank you for choosing our services!", Toast.LENGTH_LONG).show();
                                    }
                                });

                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {}
                                });

                                alert.show();
                            }
                        }
                    }
                    public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                        chosenSociety = parent.getItemAtPosition((int)id).toString();
                    }

                });

                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}