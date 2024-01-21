package fr.pamarg.owndriveapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel
{
    private final MutableLiveData<String> ip = new MutableLiveData<>();
    private final MutableLiveData<String> cpu = new MutableLiveData<>();
    private final MutableLiveData<String> ram = new MutableLiveData<>();

    public MutableLiveData<String> getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu.postValue(cpu);
    }

    public LiveData<String> getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip.postValue(ip);
    }

    public LiveData<String> getRAM()
    {
        return ram;
    }

    public void setRAM(String ram)
    {
        this.ram.postValue(ram);
    }
}
