package com.example.titlefragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A reusable title bar built as a Fragment.
 * It shows a smiley ImageButton and a title text.
 * When the button is clicked it shows a Toast and notifies the host Activity.
 */
public class TitleFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";

    /** Interface the host Activity must implement to receive button clicks. */
    public interface OnTitleButtonClickListener {
        void onTitleButtonClicked();
    }

    private OnTitleButtonClickListener listener;

    public TitleFragment() {
        super(R.layout.fragment_title);
    }

    /** Factory method: the recommended way to pass data into a Fragment. */
    public static TitleFragment newInstance(String title) {
        TitleFragment fragment = new TitleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTitleButtonClickListener) {
            listener = (OnTitleButtonClickListener) context;
        } else {
            throw new IllegalStateException(
                    context.getClass().getSimpleName()
                            + " must implement OnTitleButtonClickListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleText = view.findViewById(R.id.titleText);
        ImageButton imageButton = view.findViewById(R.id.imageButton);

        // Read the title passed through newInstance()
        Bundle args = getArguments();
        if (args != null) {
            titleText.setText(args.getString(ARG_TITLE));
        }

        imageButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), R.string.button_clicked, Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onTitleButtonClicked();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null; // avoid leaking the Activity
    }
}