package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    //fields
    private EditText editText;
    private Button sendButton;
    private Button receiveButton;
    private ListView listView;
    private ArrayList<TextMessage> listItems;
    private DBManager db;
    private StringBuffer row = new StringBuffer(), dbId = new StringBuffer(), timestamp = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        //initialize database using DBManager constructor
        db = new DBManager(this);

        //initialize views
        editText = findViewById(R.id.type_message);
        sendButton = findViewById(R.id.send_button);
        receiveButton = findViewById(R.id.receive_button);
        listView = findViewById(R.id.list_view);

        listItems = new ArrayList<>();
        MessageSend adapter = new MessageSend(this, R.layout.message_send, listItems);
        listView.setAdapter(adapter);
        LayoutInflater inflater = LayoutInflater.from(this);

        //show all messages currently in database
        Cursor allMessages = db.getAllTextMessages();
        while (allMessages.moveToNext()){
            row = new StringBuffer();
            dbId = new StringBuffer();
            timestamp = new StringBuffer();

            dbId.append(allMessages.getString(0));
            row.append(allMessages.getString(1));
            timestamp.append(allMessages.getString(3));
            listItems.add(new TextMessage(allMessages.getString(2).equals("1") ? R.drawable.male : R.drawable.female, row.toString(), inflater.inflate(allMessages.getString(2).equals("1") ? R.layout.message_send: R.layout.message_receive, null), dbId.toString(), timestamp.toString()));
            adapter.notifyDataSetChanged();//update adapter
        }
        //onClickListener for sending
        sendButton.setOnClickListener( clickListener -> {
        if (editText.getText().toString() != null) {
            Long tsLong = System.currentTimeMillis()/1000;
            String timestamp = tsLong.toString();
            boolean isInserted = db.insertData(editText.getText().toString(), 1, timestamp);

            if (isInserted){
                //Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
                Cursor res = db.getTextMessageByTimeStamp(timestamp);

                while (res.moveToNext()){
                    listItems.add(new TextMessage(R.drawable.male, editText.getText().toString(), inflater.inflate(R.layout.message_send, null), returnDbId(res), null));
                    adapter.notifyDataSetChanged();//update adapter
                }
                editText.setText("");//clear edit text
            } else {
                Toast.makeText(this, "Data Insertion Failed!", Toast.LENGTH_LONG).show();
            }

        }
        });

        //onClickListener for receiving
        receiveButton.setOnClickListener( clickListener -> {
            if (editText.getText().toString() != null) {
                Long tsLong = System.currentTimeMillis()/1000;
                String timestamp = tsLong.toString();
                boolean isInserted = db.insertData(editText.getText().toString(), 0, timestamp);
                if (isInserted){
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
                    Cursor res = db.getTextMessageByTimeStamp(timestamp);

                    while (res.moveToNext()){
                        listItems.add(new TextMessage(R.drawable.male, editText.getText().toString(), inflater.inflate(R.layout.message_send, null), returnDbId(res), null));
                        adapter.notifyDataSetChanged();//update adapter
                    }
                } else {
                    Toast.makeText(this, "Data Insertion Failed!", Toast.LENGTH_LONG).show();
                }
                editText.setText("");//clear edit
            }
        });

        listView.setOnItemClickListener((adapterView, view, position, id)-> {
            new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete this message?")
                .setMessage("The Selected Row is: " + position + "\nThe Database Id is: " + listItems.get(position).getDbId())
                .setPositiveButton("yes", (dialog, which) -> {
                    db.deleteTextMessageByTimeStamp(listItems.get(position).getTimestamp());
                    listItems.remove(position);
                    adapter.notifyDataSetChanged();
                }).setNegativeButton("no", null)
                    .show();
        });


    }
    public String returnDbId(Cursor cursor){
        return cursor.getString(0);
    }

    public String returnTimestamp(Cursor cursor){
        return cursor.getString(3);
    }

    private class MessageSend extends ArrayAdapter<TextMessage>{
        Context mCtx;
        int resource;
        List<TextMessage> textList;

        public MessageSend(Context mCtx, int resource, List<TextMessage> textList){
            super(mCtx, resource, textList);

            this.mCtx = mCtx;
            this.resource = resource;
            this.textList = textList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            TextMessage textMessage = textList.get(position);
            View view = textMessage.getView();
            ImageView imageViewSend = view.findViewById(R.id.image_send);
            TextView textViewSend = view.findViewById(R.id.text_send);


            textViewSend.setText(textList.get(position).getText());
            imageViewSend.setImageResource(textList.get(position).getImage());
            textViewSend.setOnClickListener( clickListener -> {

            });
            return view;
        }
    }

    private class TextMessage{
        private int image;
        private String text;
        private View view;
        private String dbId;
        private String timestamp;

        public View getView() {
            return this.view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public int getImage() {
            return this.image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDbId(){ return this.dbId;}

        public void setDbId(String dbId){ this.dbId = dbId;}

        public String getTimestamp(){return this.timestamp;}

        public void setTimestamp(String timestamp){this.timestamp = timestamp;}

        private TextMessage(int image, String text, View view, String dbId, String timestamp) {
            this.setImage(image);
            this.setText(text);
            this.setView(view);
            this.setDbId(dbId);
            this.setTimestamp(timestamp);
        }


    }
}
