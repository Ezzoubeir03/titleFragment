package com.example.titlefragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements TitleFragment.OnTitleButtonClickListener {

    private static final String KEY_COUNT = "key_count";

    private TextView counterText;
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterText = findViewById(R.id.counterText);

        // Restore the counter after rotation
        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt(KEY_COUNT, 0);
        }
        updateCounter();

        // Add the fragment only the first time (Android restores it after rotation)
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.title_container,
                            TitleFragment.newInstance(getString(R.string.title_text)))
                    .commit();
        }
    }

    /** Called by TitleFragment when the smiley button is clicked. */
    @Override
    public void onTitleButtonClicked() {
        clickCount++;
        updateCounter();
    }

    private void updateCounter() {
        counterText.setText(getString(R.string.click_count, clickCount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT, clickCount);
    }
}