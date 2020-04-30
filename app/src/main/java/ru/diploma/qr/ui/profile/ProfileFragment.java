package ru.diploma.qr.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }
}
