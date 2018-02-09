package br.com.iandev.midiaindoor.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.activity.ClockActivity;
import br.com.iandev.midiaindoor.view.activity.ContentActivity;
import br.com.iandev.midiaindoor.view.activity.DeviceActivity;
import br.com.iandev.midiaindoor.view.activity.InfoActivity;
import br.com.iandev.midiaindoor.view.activity.LicenseActivity;
import br.com.iandev.midiaindoor.view.activity.LogActivity;
import br.com.iandev.midiaindoor.view.activity.PersonActivity;
import br.com.iandev.midiaindoor.view.activity.PlayerActivity;
import br.com.iandev.midiaindoor.view.activity.ServiceActivity;

public class NavigationBar extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.navigation_bar_main, container, false);

        view.findViewById(R.id.button_goto_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), PlayerActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), DeviceActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), PersonActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_registry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), LicenseActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), LogActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), ServiceActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), ContentActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.button_goto_clock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), ClockActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
