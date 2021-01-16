package il.co.expertize.navigationapp.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;



import java.util.List;

import il.co.expertize.navigationapp.Model.Travel;
import il.co.expertize.navigationapp.Repository.ITravelRepository;
import il.co.expertize.navigationapp.Repository.TravelRepository;

public class MainViewModel extends AndroidViewModel {

    ITravelRepository repository;
    private MutableLiveData<List<Travel>> mutableLiveData = new MutableLiveData<>();
    public MainViewModel(Application p) {
        super(p);
        repository =  TravelRepository.getInstance(p);

        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {

                List<Travel> travelList =repository.getAllTravels();
                mutableLiveData.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);
    }
    void addTravel(Travel travel)
    {
        repository.addTravel(travel);
    }
    public void updateTravel(Travel travel)
    {
        repository.updateTravel(travel);
    }
    public MutableLiveData<List<Travel>> getAllTravels()
    {
        return mutableLiveData;
    }
    MutableLiveData<Boolean> getIsSuccess()
    {
        return repository.getIsSuccess();
    }
}
