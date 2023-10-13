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
        this.ipAddress.setValue(ipAddress);
    }
}
