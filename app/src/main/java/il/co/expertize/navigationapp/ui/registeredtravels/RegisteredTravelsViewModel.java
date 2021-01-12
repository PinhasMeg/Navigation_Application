package il.co.expertize.navigationapp.ui.registeredtravels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisteredTravelsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegisteredTravelsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Registered Travels fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}