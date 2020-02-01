package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;//init shared preferences to null
    private SharedPreferences.Editor editPref;
    private EditText editEmail, editPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3); //init layout lab3
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        sharedPref = getSharedPreferences("com.example.androidlabs.lab3", Context.MODE_PRIVATE);
        editPref = sharedPref.edit();
        editPref.putString("email", " ");

        checkSharedPreferences();
        loginButton.setOnClickListener( clickListener -> {

                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                //save email
                editPref.putString(getString(R.string.email), email);
                editPref.commit();

                //save password
                editPref.putString(getString(R.string.password), password);
                editPref.commit();

                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                goToProfile.putExtra("EMAIL", email);
                startActivity(goToProfile);
        });
    }

    private void checkSharedPreferences(){
        String email = sharedPref.getString(getString(R.string.email),"");
        String password = sharedPref.getString(getString(R.string.password), "");

        editEmail.setText(email);
        editPassword.setText(password);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}