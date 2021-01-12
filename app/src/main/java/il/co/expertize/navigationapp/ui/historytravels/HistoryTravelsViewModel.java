package il.co.expertize.navigationapp.ui.historytravels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryTravelsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryTravelsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is History Travels fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}