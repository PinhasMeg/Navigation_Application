package il.co.expertize.androidapplicationjava.ViewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import il.co.expertize.androidapplicationjava.Data.TravelRepository;
import il.co.expertize.androidapplicationjava.MainActivity;
import il.co.expertize.androidapplicationjava.Models.Travel;
import il.co.expertize.androidapplicationjava.UI.AddTravelActivity;

import static java.security.AccessController.getContext;

public class TravelViewModel extends AndroidViewModel {

    TravelRepository travelRepository;

    public TravelViewModel(@NonNull Application application) {
        super(application);
        travelRepository = new TravelRepository(application);
    }

    public void addTravel(Travel travel) {
        travelRepository.addTravel(travel);
    }

    public LiveData<Boolean> getIsSuccess()
    {
        return travelRepository.getIsSuccess();
    }
}
