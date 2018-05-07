package com.example.justin.silent_assassin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.Smoke;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PowerUps extends AppCompatActivity {
    private int moneyLeft = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_ups);

        Bundle u = getIntent().getExtras();
        final User user = u.getParcelable("user");


        final TextView moneyText = findViewById(R.id.moneyLeft);
        final String text = "Money Left: $" + moneyLeft;

        moneyText.setText(text);

        final EditText EvaderNum = findViewById(R.id.EvaderNum);
        final EditText SmokeScreenNum = findViewById(R.id.SmokeScreenNum);
        final EditText Counter = findViewById(R.id.CounterNum);
        final EditText QuickEscape = findViewById(R.id.QuickEscapeNum);

        final AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Insufficient funds");

        EvaderNum.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if((250 * Integer.valueOf(EvaderNum.getText().toString())) > moneyLeft)
                {
                    AlertDialog alert1 = build.create();
                    alert1.show();
                }

                else
                {
                    moneyLeft = moneyLeft - 250 * Integer.valueOf(EvaderNum.getText().toString());
                    String text = "Money Left: $" + moneyLeft;
                    moneyText.setText(text);
                }

                EvaderNum.setText(null);
                return true;
            }
        });

        SmokeScreenNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if((300 * Integer.valueOf(SmokeScreenNum.getText().toString())) > moneyLeft)
                {
                    AlertDialog alert1 = build.create();
                    alert1.show();
                }

                else
                {
                    moneyLeft = moneyLeft - 300 * Integer.valueOf(SmokeScreenNum.getText().toString());
                    String text = "Money Left: $" + moneyLeft;
                    moneyText.setText(text);
                }

                SmokeScreenNum.setText(null);
                return true;
            }
        });

        Counter.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if((500 * Integer.valueOf(Counter.getText().toString())) > moneyLeft)
                {
                    AlertDialog alert1 = build.create();
                    alert1.show();
                }

                else
                {
                    moneyLeft = moneyLeft - 500 * Integer.valueOf(Counter.getText().toString());
                    String text = "Money Left: $" + moneyLeft;
                    moneyText.setText(text);
                }

                Counter.setText(null);
                return true;
            }
        });

        QuickEscape.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if((700 * Integer.valueOf(QuickEscape.getText().toString())) > moneyLeft)
                {
                    AlertDialog alert1 = build.create();
                    alert1.show();
                }

                else
                {
                    moneyLeft = moneyLeft - 700 * Integer.valueOf(QuickEscape.getText().toString());
                    String text = "Money Left: $" + moneyLeft;
                    moneyText.setText(text);
                }

                QuickEscape.setText(null);
                return true;
            }
        });

        Button goToSettings = findViewById(R.id.PowerUpsToSettings);
        goToSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PowerUps.this, Settings.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        Button goToMap = findViewById(R.id.btnPlayGame);
        goToMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PowerUps.this, MapsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
