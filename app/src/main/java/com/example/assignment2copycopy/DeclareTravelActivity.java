package com.example.assignment2copycopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.GONE;

public class DeclareTravelActivity extends AppCompatActivity {
    private TextView nameTxt, studentIDTxt, mgTxt, literalMGTxt, literalStudentIDTxt;
    private EditText t1, t2, destination;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_travel);

        //Action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        nameTxt = findViewById(R.id.travelNameText);
        mgTxt = findViewById(R.id.travelMGText);
        studentIDTxt = findViewById(R.id.travelStudentIDText);
        literalMGTxt = findViewById(R.id.travelLiteralMGTxt);
        literalStudentIDTxt = findViewById(R.id.travelLiteralStudentIDText);
        t1 = findViewById(R.id.fromDate);
        t2 = findViewById(R.id.toDate);
        destination = findViewById(R.id.toDestination);

        Account user = Database.user;
        nameTxt.setText(user.getName());
        mgTxt.setText(user.getMentor_group());
        studentIDTxt.setText(user.getStudentID());
        if (user.getMentor_group().equals("null"))
        {
            mgTxt.setVisibility(GONE);
            literalMGTxt.setVisibility(GONE);
            literalStudentIDTxt.setText("NRIC:");
        }

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        db = fd.getReference("Travel");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profilePic:
                ProfileDialog profileDialog = new ProfileDialog();

                profileDialog.show(getSupportFragmentManager(),"profile dialog");
                return true;

            case R.id.settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.news:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.travel:
                intent = new Intent(getApplicationContext(), DeclareTravelActivity.class);
                startActivity(intent);
                return true;

            case R.id.temperature:
                intent = new Intent(getApplicationContext(), TemperatureActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onConfirmTravel(View view){

        String from = t1.getText().toString(),
                to = t2.getText().toString(),
                place = destination.getText().toString();

        if (from.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") &&
                to.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") &&
                !place.equals(""))
        {
            String id = db.push().getKey();
            Travel travel = new Travel(from, to, place, Database.user);
            db.child(id).setValue(travel);
            Toast toast = Toast.makeText(getApplicationContext(), "submitting declaration...", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "submission failed", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
