package fr.pamarg.owndriveapp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class LoginFragment extends Fragment {

    View view;
    MainActivityViewModel mainActivityViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.setOnApp(false);

        Button loginButton = view.findViewById(R.id.loginButton);
        EditText ipAddressEditText = view.findViewById(R.id.loginEditText);

        loginButton.setOnClickListener(v -> {
            String ip = ipAddressEditText.getText().toString();
            if(!ip.isEmpty() && validIp(ip))
            {
                mainActivityViewModel.setIpAddress(ip);
                mainActivityViewModel.setOnApp(true);
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("OwnDriveApp", Context.MODE_PRIVATE);
                sharedPreferences.getString("ip", ip);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_filesFragment);
            }
            else
            {
                ipAddressEditText.setError("Please enter your IP address");
            }

        });
        return view;
    }


    private boolean validIp(String ip)
    {
        String[] ipSplit = ip.split("\\.");
        if(ipSplit.length != 4)
        {
            return false;
        }
        for(String s : ipSplit)
        {
            try
            {
                int i = Integer.parseInt(s);
                if(i < 0 || i > 255)
                {
                    return false;
                }
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        return true;
    }
}