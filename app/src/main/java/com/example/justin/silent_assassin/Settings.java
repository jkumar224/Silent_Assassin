package com.example.justin.silent_assassin;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.TimedText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private String Username;
    private String StartSleep;
    private String EndSleep;
    private int miles;
    private int range;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle u = getIntent().getExtras();
        final User user = u.getParcelable("user");

        final EditText Name = findViewById(R.id.UserEntry);
        final SeekBar AssasRange = findViewById(R.id.range);
        final EditText Start = findViewById(R.id.SleepStart);
        final EditText End = findViewById(R.id.sleepEnd);
        final TextView mil = findViewById(R.id.Miles);

        AssasRange.setMax(500);
        AssasRange.setProgress(0);

        AssasRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                range = i;
                mil.setText(String.valueOf(range));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        Name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                Username = Name.getText().toString();
                Name.setText(Username);
                return true;
            }
        });

        Button Topower = findViewById(R.id.Settings_To_Power_Ups);
        Topower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Settings.this, PowerUps.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
