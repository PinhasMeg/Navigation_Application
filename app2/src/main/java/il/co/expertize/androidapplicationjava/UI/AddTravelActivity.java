package il.co.expertize.androidapplicationjava.UI;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import il.co.expertize.androidapplicationjava.Models.Travel;
import il.co.expertize.androidapplicationjava.R;
import il.co.expertize.androidapplicationjava.ViewModel.TravelViewModel;

import static il.co.expertize.androidapplicationjava.Models.Travel.CompanyConverter.CreateHashMapfromString;
import static il.co.expertize.androidapplicationjava.Models.UserLocation.convertFromLocation;
import static il.co.expertize.androidapplicationjava.Utils.Utils.PrintColorToast;
import static il.co.expertize.androidapplicationjava.Utils.Utils.isNumeric;


public class AddTravelActivity extends AppCompatActivity {
    Button button;
    EditText name;
    EditText email;
    EditText phone_number;
    EditText departure_address;
    EditText destination_address;
    EditText number_of_passengers;
    DatePicker departure_date;
    DatePicker return_date;

    private TravelViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_activity);
        button = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone_number = (EditText) findViewById(R.id.phone);
        departure_address = (EditText) findViewById(R.id.DepartureAddress);
        destination_address = (EditText) findViewById(R.id.DestinationAddress);
        number_of_passengers = (EditText) findViewById(R.id.numOfPassengers);
        departure_date = (DatePicker) findViewById(R.id.DepartureDate);
        return_date = (DatePicker) findViewById(R.id.ReturnDate);

        viewModel = new ViewModelProvider(this).get(TravelViewModel.class);

        final LiveData<Boolean> isSuccess = viewModel.getIsSuccess();
        isSuccess.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (isSuccess.getValue()) {
                    PrintColorToast(getApplicationContext(), "Good Job My friend!!", Color.GREEN);
                    name.setText("");
                    email.setText("");
                    phone_number.setText("");
                    number_of_passengers.setText("");
                    destination_address.setText("");
                    departure_address.setText("");
                } else
                    PrintColorToast(getApplicationContext(), "Data not entered properly", Color.RED);
            }
        });
    }

    public void SendRequest(View view) {

        LocalDate returnDate = null, departureDate = null, applicationDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationDate = LocalDate.now();
            returnDate = LocalDate.of(return_date.getYear(), return_date.getMonth() + 1, return_date.getDayOfMonth());
            departureDate = LocalDate.of(departure_date.getYear(), departure_date.getMonth() + 1, departure_date.getDayOfMonth());
            if (returnDate.isBefore(departureDate)) {
                PrintColorToast(getApplicationContext(), "Please enter return date later than the departure date", Color.RED);
                return;
            } else if (departureDate.isBefore(LocalDate.now())) {
                PrintColorToast(getApplicationContext(), "We cannot enter departure date earlier than the current day!", Color.RED);
                return;
            }
        }


        if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                phone_number.getText().toString().isEmpty() || number_of_passengers.getText().toString().isEmpty() ||
                departure_address.getText().toString().isEmpty() || destination_address.getText().toString().isEmpty()) {
            PrintColorToast(getApplicationContext(), "Please fill all the information", Color.RED);
        } else if (!isNumeric(phone_number.getText().toString()) || !isNumeric(number_of_passengers.getText().toString()))
            PrintColorToast(getApplicationContext(), "Please enter Numbers", Color.RED);

        else {
            Travel travel = new Travel();
            travel.setClientName(name.getText().toString());
            travel.setClientEmail(email.getText().toString());
            travel.setClientPhone(phone_number.getText().toString());
            travel.setNumOfPassengers(number_of_passengers.getText().toString());
            travel.setDestinationAddress(destination_address.getText().toString());
            travel.setTravelDate(departureDate.toString());
            travel.setApplicationDate(applicationDate.toString());
            travel.setArrivalDate(returnDate.toString());
            travel.setTravelLocation(convertFromLocation(makeLocation(departure_address.getText().toString())));
            travel.setRequesType(Travel.RequesType.sent);
            travel.setCompany(CreateHashMapfromString("NO:false"));
            viewModel.addTravel(travel);
        }
    }

    public Location makeLocation(String address) {
        Geocoder geocoder = new Geocoder(this);
        Location travelLocation = new Location("travelLocation");
        try {
            List<Address> list = geocoder.getFromLocationName(address, 1);
            if (!list.isEmpty()) {
                Address temp = list.get(0);
                travelLocation.setLatitude(temp.getLatitude());
                travelLocation.setLongitude(temp.getLongitude());
            } else {
                PrintColorToast(getApplicationContext(), "Unable to understand address", Color.RED);
            }
        } catch (IOException e) {
            PrintColorToast(getApplicationContext(), "Unable to understand address. Check Internet connection.", Color.RED);
        }
        return travelLocation;
    }
}
