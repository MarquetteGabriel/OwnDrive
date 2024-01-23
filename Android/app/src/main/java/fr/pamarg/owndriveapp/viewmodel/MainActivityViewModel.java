package fr.pamarg.owndriveapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Folders;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<String> ipAddress = new MutableLiveData<>();
    private final MutableLiveData<Folders> treeFolders = new MutableLiveData<>();

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

    public void setTreeFolders(Folders folders)
    {
        this.treeFolders.setValue(folders);
    }

    public LiveData<Folders> getTreeFolders()
    {
        return this.treeFolders;
    }

    private final MutableLiveData<Boolean> isCopy = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getIsCopy() {
        return this.isCopy;
    }

}
