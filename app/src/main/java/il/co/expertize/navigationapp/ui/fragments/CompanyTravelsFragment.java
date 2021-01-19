package il.co.expertize.navigationapp.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import il.co.expertize.navigationapp.Adapters.CustomListAdapterCompanyTravels;
import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.R;
import il.co.expertize.navigationapp.ui.MainViewModel;


public class CompanyTravelsFragment extends Fragment {

    ListView itemsListView;
    MainViewModel mViewModel;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_companytravels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        itemsListView  = (ListView)view.findViewById(R.id.list_view_items_companytravels);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.addRemoveTravel();
        mViewModel.getAllTravels().observe(getViewLifecycleOwner(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> travelArrayList = new ArrayList<Travel>(travels);

                //create adapter object
                CustomListAdapterCompanyTravels adapter = new CustomListAdapterCompanyTravels(context, travelArrayList);

                adapter.setListener(new CustomListAdapterCompanyTravels.CompanyTravelListener() {
                    @Override
                    public void onButtonClicked(int position, View view) {
                        if (view.getId() == R.id.call_User) {
                            String phone = travelArrayList.get(position).getClientPhone();

                            if (phone.isEmpty()) {
                                Toast.makeText(getContext(), "no phone number exist", Toast.LENGTH_LONG).show();
                            } else {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + phone));
                                startActivity(callIntent);
                            }
                        }
                        if (view.getId() == R.id.send_mail) {
                            String mail = travelArrayList.get(position).getClientEmail();
                            if (mail.isEmpty()) {
                                Toast.makeText(getContext(), "no mail address exist", Toast.LENGTH_LONG).show();
                            } else {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{travelArrayList.get(position).getClientEmail() });
                                email.putExtra(Intent.EXTRA_SUBJECT, "About Travel");
                                email.putExtra(Intent.EXTRA_TEXT, "Hello, about the travel you need...");
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }
                        }

                        if (view.getId() == R.id.validate_travel) {
                            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            final EditText edittext = new EditText(context);
                            alert.setTitle("Please enter the name of your Society");
                            alert.setView(edittext);

                            alert.setPositiveButton("Send proposal", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String societyEmail = edittext.getText().toString();
                                    if (societyEmail.contains("/")||societyEmail.contains(".")||societyEmail.contains("#")||societyEmail.contains("$")||societyEmail.contains("[")||societyEmail.contains("]"))
                                        Toast.makeText(getContext(), "Keys must not contain '/', '.', '#', '$', '[', or ']'", Toast.LENGTH_LONG).show();

                                    else {
                                        Travel currentTravel = travelArrayList.get(position);
                                        HashMap<String, Boolean> company = currentTravel.getCompany();
                                        company.remove("NO");
                                        company.put(societyEmail, false);
                                        mViewModel.updateTravel(currentTravel);
                                        Toast.makeText(getContext(), "Your proposition has been successfully sent. Please wait for the client response.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {}
                            });

                            alert.show();
                        }
                    }
                });
                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }
}