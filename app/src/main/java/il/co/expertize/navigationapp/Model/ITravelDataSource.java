package il.co.expertize.navigationapp.Model;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface ITravelDataSource {
    void addTravel(Travel travel);
    void addRemoveTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();

    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(NotifyToTravelListListener l);
}
