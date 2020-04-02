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

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
    }

    public void onSignInClicked(View view){
        //getting the strings
        EditText idTxt = findViewById(R.id.signUpStudentID),
                passwordTxt = findViewById(R.id.signUpPassword),
                mgTxt = findViewById(R.id.signUpMG),
                nameTxt = findViewById(R.id.signUpName);

        String studentID = idTxt.getText().toString(),
                name = nameTxt.getText().toString(),
                password = passwordTxt.getText().toString(),
                mentor_grp = mgTxt.getText().toString();

        if(!studentID.equals("") &&
                !name.equals("") &&
                !password.equals("") &&
                !mentor_grp.equals("") &&
                (studentID.matches("[hH](1[56789]|20)[13]0[0-9]{3}")
                        || studentID.matches("[SFGstfg][0-9]{7}[A-Za-z]")))
        //if everything is added
        //rough validation of student ID
        //rough validation of NRIC
        {
            //adding an account to firebase
            String id = db.push().getKey();
            Account acc = new Account(name, mentor_grp, password, id, studentID);
            db.child(id).setValue(acc);
            Database.user = acc;

            //go back to main activity
            Database.logged_in = true;
            Toast toast = Toast.makeText(getApplicationContext(), "going back to home screen", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else if(!(studentID.matches("[hH](1[56789]|20)[13]0[0-9]{3}") || studentID.equals("null"))){
            Toast toast = Toast.makeText(getApplicationContext(), "please check your student ID (hYYX0XXX or null)", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "please fill in all text fields", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}