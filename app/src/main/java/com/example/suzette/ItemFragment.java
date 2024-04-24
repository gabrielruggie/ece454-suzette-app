package com.example.suzette;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ItemFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";

    private String text;

    // Factory method to create a new instance of the fragment with a given text
    public static ItemFragment newInstance(String text) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the text from arguments
        if (getArguments() != null) {
            text = getArguments().getString(ARG_TEXT);
        }

        // Access the views defined in fragment_item.xml
        // and set text or click listener as necessary
        TextView textView = view.findViewById(R.id.textView);
        Button button = view.findViewById(R.id.button);

        textView.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                //openChat();
            }
        });
    }

    private void openChat() {
        // Open another activity here
        Intent intent = new Intent(getActivity(), CookingActivity.class);
        startActivity(intent);
    }
}