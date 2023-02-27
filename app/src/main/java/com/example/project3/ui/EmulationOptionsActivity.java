package com.example.project3.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.project3.R;

/* View for emulation settings page */
public class EmulationOptionsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pref);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle(getString(R.string.emulator_prefs));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.content, new EmulationOptionsFragment()).commit();
    }

    public static class EmulationOptionsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_emulation);
        }
    }
}