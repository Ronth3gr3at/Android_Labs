package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        //BUTTON
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener( v -> {Toast.makeText(getApplicationContext(),"Here is more information", Toast.LENGTH_LONG).show();});

        //CHECKBOX
       CheckBox chk = findViewById(R.id.checkbox);
        chk.setOnCheckedChangeListener((CompoundButton check, boolean state) -> {
            String message;
            if (state){
                message = "on";
            } else {
                message = "off";
            }
            Snackbar.make(chk,"The checkbox is: " + message,Snackbar.LENGTH_LONG).setAction("undo", click -> check.setChecked(!state)).show();

        });
        //SWITCH
       Switch swi = findViewById(R.id.Switch);
        swi.setOnCheckedChangeListener( (CompoundButton swtch, boolean state) -> {
            String message;
            if (state){
                message = "on";
            } else {
                message = "off";
            }
            Snackbar.make(swtch,"The switch is: " + message,Snackbar.LENGTH_LONG).setAction("undo", click -> swtch.setChecked(!state)).show();

        });
    }



}