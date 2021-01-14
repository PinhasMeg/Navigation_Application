package il.co.expertize.androidapplicationjava.Data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.co.expertize.androidapplicationjava.Models.Travel;

public class TravelFirebaseDataSource {
    private final MutableLiveData<Boolean> isSuccess= new MutableLiveData<>();


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("Travels");

    TravelFirebaseDataSource() {}

    private static TravelFirebaseDataSource instance;

    public static TravelFirebaseDataSource getInstance() {
        if (instance == null)
            instance = new TravelFirebaseDataSource();
        return instance;
    }

    public void addTravel(Travel travel) {
        String id = travels.push().getKey();
        travel.setTravelId(id);
        assert id != null;
        travels.child(id).setValue(travel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isSuccess.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isSuccess.setValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getIsSuccess() {return isSuccess;}
}
