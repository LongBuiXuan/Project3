package com.example.project3.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.example.project3.BuildConfig;
import com.example.project3.R;

public class AboutActivity extends AppCompatActivity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle(getString(R.string.about_title));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set dynamic fields (version and build date)
        TextView version = (TextView)findViewById(R.id.text_version);
        TextView date = (TextView)findViewById(R.id.text_date);
        version.setText(String.format(getString(R.string.app_version_format), BuildConfig.VERSION_NAME));
        //date.setText(dateFormat.format(BuildConfig.TIMESTAMP));
    }
}