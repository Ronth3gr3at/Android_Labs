package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
private ImageButton imageButton;
private static final int REQUEST_IMAGE_CAPTURE = 1;
static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
private String functionName;
private EditText emailEditText;
private Button chatroomButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //VIEWS
        imageButton = findViewById(R.id.image_button);
        emailEditText = findViewById(R.id.email_profile);
        chatroomButton = findViewById(R.id.chat_button);

        //OnClick Listeners
        imageButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        chatroomButton.setOnClickListener( clickListener -> {
            Intent goToChatRoom = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(goToChatRoom);
        });


        Intent fromMain = getIntent();

        //change view text
        emailEditText.setText(fromMain.getStringExtra("EMAIL"));

        //LOG
        functionName = "onCreate";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

        functionName = "dispatchTakePictureIntent";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
        functionName = "onActivityResult";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onStart(){
        super.onStart();
        functionName = "onStart";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onResume(){
        super.onResume();
        functionName = "onResume";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onPause(){
        super.onPause();
        functionName = "onPause";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onStop(){
        super.onStop();
        functionName = "onStop";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        functionName = "onDestroy";
        Log.e(ACTIVITY_NAME, "In function: " + functionName);
    }
}
