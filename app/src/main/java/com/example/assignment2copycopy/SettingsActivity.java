package com.example.assignment2copycopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity implements ChangePasswordDialog.passwordChangeListener {

    EditText editName, editMG, editID;
    TextView mgTxt;
    Button password, edit, confirm;
    DatabaseReference db;
    static boolean toggleBool = false; //true allows editing, false does not
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        db = fd.getReference("Account");
        id = Database.user.getFirebaseID();

        //Action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView idOrNRIC = findViewById(R.id.setIDText);
        mgTxt = findViewById(R.id.setMGText);
        editName = findViewById(R.id.editName);
        editID = findViewById(R.id.editID);
        editMG = findViewById(R.id.editMG);
        password = findViewById(R.id.openPasswordChange);
        edit = findViewById(R.id.toggleSettings);
        confirm = findViewById(R.id.confirmSettings);

        Account user = Database.user;

        editName.setText(user.getName());
        editID.setText(user.getStudentID());
        editMG.setText(user.getMentor_group());

        if (Database.user.getStudentID().matches("[hH].+"))
        {
            idOrNRIC.setText("Student ID:");
        }
        else
        {
            idOrNRIC.setText("NRIC:");
            mgTxt.setVisibility(View.GONE);
            editMG.setVisibility(View.GONE);
        }

        toggle();
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

                    if(acc.getFirebaseID() == id){
                        Database.user = acc;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("error: ", error.getMessage());
            }
        });
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

    public void onToggleSettings(View view){
        toggleBool = !toggleBool;
        toggle();
    }

    public void onConfirmChanges(View view){
        toggleBool = false;
        String name = editName.getText().toString(),
                mg = editMG.getText().toString(),
                studentID = editID.getText().toString();
        Account user = Database.user;
        db.child(id).setValue(new Account( name, mg, user.getPassword(), id, studentID ) );
        Toast toast = Toast.makeText(getApplicationContext(), "updating settings...", Toast.LENGTH_LONG);
        toast.show();
        toggle();
    }

    private void toggle(){
        if (toggleBool == true){
            //if editing
            password.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
            editMG.setInputType(View.AUTOFILL_TYPE_TEXT);
            editID.setInputType(View.AUTOFILL_TYPE_TEXT);
            editName.setInputType(View.AUTOFILL_TYPE_TEXT);
        }
        else{
            password.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
            editMG.setInputType(View.AUTOFILL_TYPE_NONE);
            editID.setInputType(View.AUTOFILL_TYPE_NONE);
            editName.setInputType(View.AUTOFILL_TYPE_NONE);
        }
    }

    public void onOpenChangePassword(View view){
        toggleBool = false;
        toggle();
        ChangePasswordDialog cpDialog = new ChangePasswordDialog();
        cpDialog.show(getSupportFragmentManager(),"change password dialog");
    }

    @Override
    public void changePassword(String newpassword){
        Database.user.setPassword(newpassword);
        db.child(id).setValue(Database.user);
        Toast toast = Toast.makeText(getApplicationContext(), "changing password", Toast.LENGTH_LONG);
        toast.show();
    }
}
