package il.co.expertize.navigationapp.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import il.co.expertize.navigationapp.Model.Travel;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void addRemoveTravel(Travel travel);
    void updateTravel(Travel travel);
    public ArrayList<Travel> checkTravelsByUser(List<Travel> travels);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
