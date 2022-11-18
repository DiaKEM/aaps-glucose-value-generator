package com.diakem.aapsvaluegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonView = (Button) findViewById(R.id.send);
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        EditText glucoseValueView = (EditText) findViewById(R.id.glucoseValue);
                        int value = Integer.parseInt(glucoseValueView.getText().toString());

                        RadioButton selectedTrend = (RadioButton) findViewById(
                                ((RadioGroup) findViewById(R.id.trendArrow)).getCheckedRadioButtonId()
                        );
                        String trendArrow = selectedTrend.getText().toString();
                        Intent mainIntent = new Intent();
                        mainIntent.setPackage("info.nightscout.androidaps");
                        mainIntent.setAction("com.dexcom.g7.EXTERNAL_BROADCAST");

                        // Create main bundle
                        Bundle mainBundle = new Bundle();
                        Bundle valueBundle = new Bundle();
                        valueBundle.putInt("glucoseValue", value);
                        valueBundle.putLong("timestamp", System.currentTimeMillis() / 1000);
                        valueBundle.putString("trendArrow", trendArrow);
                        mainBundle.putBundle("0", valueBundle);
                        mainIntent.putExtra("glucoseValues", mainBundle);

                        sendBroadcast(mainIntent);

                        Toast.makeText(view.getContext(), String.valueOf(value) + " with " + trendArrow + " send to AAPS", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}