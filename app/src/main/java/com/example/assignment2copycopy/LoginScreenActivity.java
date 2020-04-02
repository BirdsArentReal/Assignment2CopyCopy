package com.example.assignment2copycopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreenActivity extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        db = fd.getReference("Account");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Read from the database
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Database.accLst.clear();
                for(DataSnapshot accSnapshot: dataSnapshot.getChildren()){
                    Account acc = accSnapshot.getValue(Account.class);
                    Database.accLst.add(acc);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("error: ", error.getMessage());
            }
        });

        //artificially making an update to initialise accLst
        String id = db.push().getKey();
        Account test = new Account("temp", "temp", "temp", "temp", "temp");
        db.child(id).setValue(test);
        db.child(id).removeValue();
    }

    public void onSignUpClicked(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "going to sign up screen", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);

    }
    public void onLoginClicked(View view){
        EditText studentIDTxt = findViewById(R.id.loginStudentID),
                passwordTxt = findViewById(R.id.loginPassword);

        String studentID = studentIDTxt.getText().toString(),
                password = passwordTxt.getText().toString();

        for (Account acc: Database.accLst){
            if (studentID.equals(acc.getStudentID()) && password.equals(acc.getPassword())){
                Database.logged_in = true;
                Database.user = acc;
                break;
            }
        }

        if(!Database.logged_in){
            Toast toast = Toast.makeText(getApplicationContext(), "username and password do not match, or does not exist", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        Toast toast = Toast.makeText(getApplicationContext(), "going back to home screen", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

}
