package fr.pamarg.owndriveapp.Network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.pamarg.owndriveapp.MainActivityViewModel;

public class Connection
{

    private final MainActivityViewModel mainActivityViewModel;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Context context;

    private boolean state;
    private Socket a_socket;
    private final int a_timeout = 3000;

    public Connection (Context context, MainActivityViewModel mainActivityViewModel)
    {
        this.mainActivityViewModel = mainActivityViewModel;
        this.context = context;
    }



    public void connect(String p_ip, Integer p_port)
    {
        Runnable runnable = () ->
        {
            try
            {
                this.a_socket = new Socket();
                this.a_socket.connect(new InetSocketAddress(p_ip, p_port), a_timeout);

                if(a_socket.isConnected())
                {
                    mainActivityViewModel.setState(true);
                }
                else
                {
                    mainActivityViewModel.setState(false);
                }

            }
            catch (IOException e)
            {
                Log.i("TAG", e.getMessage());
                mainActivityViewModel.setState(false);
            }
        };
        executorService.execute(runnable);
    }
}
