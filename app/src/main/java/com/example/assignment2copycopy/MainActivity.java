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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements NewsDialog.newsChangeListener{
    TextView title, body;
    FloatingActionButton fab;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        title = findViewById(R.id.newsTitle);
        body = findViewById(R.id.newsBody);

        //floating action button if user is a staff
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Database.user.getStudentID().matches("[hH].*")){
                    Toast toast = Toast.makeText(getApplicationContext(), "only staff are allowed to change the news", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                NewsDialog newsDialog = new NewsDialog();
                newsDialog.show(getSupportFragmentManager(), "change news dialog");
            }
        });

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        db = fd.getReference("News");


        //start with login
        boolean logged_in = Database.logged_in;
        if(!logged_in){
            Toast toast = Toast.makeText(getApplicationContext(), "going to login screen", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class);
            startActivity(intent);
        }


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
                News news = dataSnapshot.getValue(News.class);
                title.setText(news.getTitle());
                body.setText(news.getBody());
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


    @Override
    public void newNews(String newTitle, String newBody) {
        News news = new News(newTitle, newBody);
        db.setValue(news);
        Toast toast = Toast.makeText(getApplicationContext(), "updating news...", Toast.LENGTH_LONG);
        toast.show();
    }
}

