package ru.diploma.qr.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ru.diploma.qr.R;

public class ProfileFragment extends Fragment {

    private LinearLayout llGenerate;
    private LinearLayout llHistory;
    private LinearLayout llContactUs;
    private LinearLayout llHelp;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean isChecked = sharedPreferences.getBoolean("checkBox", true);
        llGenerate = view.findViewById(R.id.ll_generate);
        llGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_notifications_to_generatedHistory);
            }
        });
        llHistory = view.findViewById(R.id.ll_scanner);
        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_notifications_to_scanHistory);

            }
        });
        llContactUs = view.findViewById(R.id.ll_contact_us);
        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_notifications_to_fragmentContactUs);
            }
        });
        llHelp = view.findViewById(R.id.ll_help);
        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_notifications_to_fragmentHelp);
            }
        });
        checkBox = view.findViewById(R.id.volume_check_box);
        if (isChecked) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(radiobuttonClickListener);

    }

    View.OnClickListener radiobuttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkBox.isChecked()) {
                editor.putBoolean("checkBox", true);
                editor.apply();
            } else {
                editor.putBoolean("checkBox", false);
                editor.apply();
            }
        }
    };
}
