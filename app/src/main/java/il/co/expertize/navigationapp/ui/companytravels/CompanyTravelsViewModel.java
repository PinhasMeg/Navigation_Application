package il.co.expertize.navigationapp.ui.companytravels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompanyTravelsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompanyTravelsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Company Travels fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}