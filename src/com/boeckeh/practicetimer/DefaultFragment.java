package com.boeckeh.practicetimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DefaultFragment extends Fragment {
    public DefaultFragment() {
    }

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.LEFT);
        Bundle args = getArguments();
        textView.setText("What do you say? " + Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
        return textView;
    }

}
