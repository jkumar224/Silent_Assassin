package com.example.justin.silent_assassin;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase dbase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = dbase.getReference("users");

        final AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Incorrect Username or password");

        Button nextPage = findViewById(R.id.btnNextPage);
        nextPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView TXTNAME = findViewById(R.id.inputUsrName);
                TextView PASSW = findViewById(R.id.inputPassword);
                final String name = TXTNAME.getText().toString();
                final String Pass = PASSW.getText().toString();
                final User First = new User(name, Pass);

                Query query = myRef.orderByChild("UserName").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (!dataSnapshot.exists())
                        {
                            myRef.child(name).setValue(First);
                            AlertDialog alert1 = build.create();
                            alert1.show();
                        }

                        else
                        {
                            User M = dataSnapshot.child(name).getValue(User.class);

                            if(M.Password.equals(Pass))
                            {
                                Intent intent = new Intent(MainActivity.this, Settings.class);
                                intent.putExtra("user", M);
                                startActivity(intent);
                            }

                            else
                            {
                                AlertDialog alert1 = build.create();
                                alert1.show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });
            }
        });
    }
}
