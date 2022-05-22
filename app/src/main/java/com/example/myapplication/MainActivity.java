package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    int id1, id2;
    String str = "";
    private FirebaseAuth firebaseAuth;
    TextView ifnhietdo, ifdoam;
    RelativeLayout button1,button2,mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        button1 = findViewById(R.id.khung1); //Tìm lại button
        button1.setOnClickListener(this);
        mic = findViewById(R.id.khung2);
        mic.setOnClickListener(this);
        button2 = findViewById(R.id.khung3);
        button2.setOnClickListener(this);
        ifnhietdo = findViewById(R.id.nhietdo2);
        ifdoam = findViewById(R.id.doam2);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notification","my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.khung1:
                if (view.getId() == R.id.khung1 && id1 == 1) {
                    Toast.makeText(MainActivity.this, "Bạn đa tat den", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("den/bep");
                    id1 = 0;
                    myRef.setValue(id1);
                } else if (view.getId() == R.id.khung1 && id1 == 0) {
                    Toast.makeText(MainActivity.this, "Bạn đa bat den", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("den/bep");
                    id1 = 1;
                    myRef.setValue(id1);
                }
                break;

            case R.id.khung3:
                if (view.getId() == R.id.khung3 && id2 == 1) {
                    Toast.makeText(MainActivity.this, "Bạn đa tat den", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("den/phongkhach");
                    id2 = 0;
                    myRef.setValue(id2);
                } else if (view.getId() == R.id.khung3 && id2 == 0) {
                    Toast.makeText(MainActivity.this, "Bạn đa bat den", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("den/phongkhach");
                    id2 = 1;
                    myRef.setValue(id2);
                }

        }

    }


    private void getData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("test/nhietdo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float valuenhietdo = snapshot.getValue(Float.class);
                ifnhietdo.setText(String.valueOf(valuenhietdo));
                if(valuenhietdo>40)
                {
                    displayNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("test/doam").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float valuedoam = snapshot.getValue(Float.class);
                ifdoam.setText(String.valueOf(valuedoam));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("den/bep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id1 = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("den/phongkhach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id2 = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void displayNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"notification");
        builder.setSmallIcon(R.drawable.ic_mic);
        builder.setContentTitle("Notification Title");
        builder.setContentText("Message");
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1,builder.build());
    }
}