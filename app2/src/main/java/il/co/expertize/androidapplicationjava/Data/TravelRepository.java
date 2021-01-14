package il.co.expertize.androidapplicationjava.Data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import il.co.expertize.androidapplicationjava.Models.Travel;

public class TravelRepository extends Application {
    TravelFirebaseDataSource firebaseDataSource = TravelFirebaseDataSource.getInstance();
    public  TravelRepository(){}
    public  TravelRepository(Context app)   {}

    public void addTravel(Travel travel)
    {
        firebaseDataSource.addTravel(travel);
    }

    public LiveData<Boolean> getIsSuccess() {
        return firebaseDataSource.getIsSuccess();
    }
}
