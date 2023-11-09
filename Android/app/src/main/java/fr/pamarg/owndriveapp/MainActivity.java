package fr.pamarg.owndriveapp;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.pamarg.owndriveapp.Network.Connection;

public class MainActivity extends AppCompatActivity {


    private Connection connection;
    MainActivityViewModel mainActivityViewModel;
    private ExecutorService executorService;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        executorService = Executors.newSingleThreadExecutor();
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        connection = new Connection(this, mainActivityViewModel);
    }

    public void onlickConnect(View v) {
        try {
            String ip = ((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString();
            Integer port = Integer.parseInt(((TextView) findViewById(R.id.editTextTextPersonName2)).getText().toString());
            connection.connect(ip, port);
        } catch (Exception e) {
            Toast.makeText(this, "Erreur insert", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkState(View v)
    {
        if(Boolean.TRUE.equals(mainActivityViewModel.getState().getValue()))
        {
            Toast.makeText(this, "Connect", Toast.LENGTH_LONG).show();
        }
        else if(Boolean.FALSE.equals(mainActivityViewModel.getState().getValue()))
        {
            Toast.makeText(this, "No Connect", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickgetIp(View v)
    {
        getIpAddress();
    }

    public void getIpAddress()
    {
        Runnable runnable = () -> {
            ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
            Network network = connectivityManager.getActiveNetwork();
            LinkProperties linkProperties = connectivityManager.getLinkProperties(network);
            if(connectivityManager.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            {
                for (LinkAddress address : linkProperties.getLinkAddresses())
                {
                    if (!Objects.requireNonNull(address.getAddress().getHostAddress()).contains(":"))
                    {
                        String ipaddress = address.getAddress().getHostAddress();
                        mainActivityViewModel.setIpAddress(ipaddress);
                        runOnUiThread(() -> {
                            textView.setText(mainActivityViewModel.getIpAddress().getValue());
                            ((EditText) findViewById(R.id.editTextTextPersonName)).setText(mainActivityViewModel.getIpAddress().getValue());
                        });
                        break;
                    }
                }
                String ipname = linkProperties.getDomains();
            }
        };
        executorService.submit(runnable);
    }

}