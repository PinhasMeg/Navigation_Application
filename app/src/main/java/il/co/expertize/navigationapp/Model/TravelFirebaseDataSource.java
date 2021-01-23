package il.co.expertize.navigationapp.Model;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static il.co.expertize.navigationapp.MainActivity.clientEmail;

public  class TravelFirebaseDataSource implements  ITravelDataSource{

    private static final String TAG = "Firebase";

    private MutableLiveData<Boolean> isSuccess= new MutableLiveData<>();
    private List<Travel> allTravelsList;

    private ITravelDataSource.NotifyToTravelListListener notifyToTravelListListener;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("Travels");

    private static TravelFirebaseDataSource instance;

    public static TravelFirebaseDataSource getInstance() {
        if (instance == null)
            instance = new TravelFirebaseDataSource();
        return instance;
    }

    private TravelFirebaseDataSource() {
        allTravelsList = new ArrayList<>();

        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allTravelsList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Travel travel = snapshot.getValue(Travel.class);
                        allTravelsList.add(travel);
                    }
                }
                if (notifyToTravelListListener != null)
                    notifyToTravelListListener.onTravelsChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setNotifyToTravelListListener(NotifyToTravelListListener l) {
        notifyToTravelListListener = l;
    }

    @Override
    public void addTravel(Travel p) {
        String id = travels.push().getKey();
        p.setTravelId(id);
        travels.child(id).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void addRemoveTravel(Travel p)
    {
        String id = travels.push().getKey();
        p.setTravelId(id);
        travels.child(id).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        travels.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }

    public  void removeTravel(String id) {
        travels.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }

    @Override
    public void updateTravel(final Travel toUpdate) {
        removeTravel(toUpdate.getTravelId());
        addTravel(toUpdate);
    }

    @Override
    public List<Travel> getAllTravels() {
        return allTravelsList;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    @Override
    public ArrayList<Travel> checkTravelsByUser(List<Travel> travels) {

        ArrayList<Travel> newList= new ArrayList<Travel>();
        for (Travel travel: travels) {
            if(travel.getClientEmail() != null && travel.getClientEmail().equals(clientEmail))
                newList.add(travel);
        }
        return newList;
    }
}
