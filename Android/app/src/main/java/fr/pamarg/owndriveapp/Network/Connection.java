package fr.pamarg.owndriveapp.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.pamarg.owndriveapp.MainActivityViewModel;

public class Connection
{

    private final MainActivityViewModel mainActivityViewModel;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Context context;

    public Connection (Context context)
    {
        this.mainActivityViewModel = new MainActivityViewModel();
        this.context = context;
    }

    public void getIpAddress()
    {
        executorService.submit(() -> {
            ConnectivityManager connectivityManager = context.getSystemService(ConnectivityManager.class);
            Network network = connectivityManager.getActiveNetwork();
            LinkProperties linkProperties = connectivityManager.getLinkProperties(network);

            if(connectivityManager.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            {
                for (LinkAddress address : linkProperties.getLinkAddresses())
                {
                    String ipAddress = address.getAddress().getHostAddress();
                    if (ipAddress != null && !ipAddress.contains(":")) {
                        mainActivityViewModel.setIpAddress(ipAddress);
                        break;
                    }
                }
            }
        });
    }
}
