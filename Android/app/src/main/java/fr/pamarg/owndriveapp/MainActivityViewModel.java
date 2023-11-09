package fr.pamarg.owndriveapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<String> ipAddress = new MutableLiveData<>();

    public LiveData<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress.postValue(ipAddress);
    }

    private final MutableLiveData<Boolean> state = new MutableLiveData<>();

    public LiveData<Boolean> getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state.postValue(state);
    }

}
