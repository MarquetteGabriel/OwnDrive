package fr.pamarg.owndriveapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import fr.pamarg.owndriveapp.R;

public class ProfileFragment extends Fragment {

    TextView textView;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textView = view.findViewById(R.id.textView2);
        Switch switchMode = view.findViewById(R.id.modeSwitch);

        if(!switchMode.isChecked())
        {
            hideDevelopperMode();
        }

        switchMode.setOnClickListener(v -> {
            if(!switchMode.isChecked())
            {
                hideDevelopperMode();
            }
            else
            {
                showDevelopperMode();
            }
        });


        return view;
    }

    private void hideDevelopperMode()
    {
        textView.setVisibility(View.INVISIBLE);
    }

    private void showDevelopperMode()
    {
        textView.setVisibility(View.VISIBLE);
    }
}