package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //initialize views
        editText = findViewById(R.id.type_message);
        sendButton = findViewById(R.id.send_button);
        receiveButton = findViewById(R.id.receive_button);
        listView = findViewById(R.id.list_view);

        listItems = new ArrayList<>();
        MessageSend adapter = new MessageSend(this, R.layout.message_send, listItems);
        listView.setAdapter(adapter);
        LayoutInflater inflater = LayoutInflater.from(this);

        //onclicklistener for sending
        sendButton.setOnClickListener( clickListener -> {
        if (editText.getText().toString() != null) {
            listItems.add(new TextMessage(R.drawable.male, editText.getText().toString(), inflater.inflate(R.layout.message_send, null)));
            editText.setText("");//clear edit text
            adapter.notifyDataSetChanged();//update adapter
        }
        });

        receiveButton.setOnClickListener( clickListener -> {
            if (editText.getText().toString() != null) {
                listItems.add(new TextMessage(R.drawable.female, editText.getText().toString(), inflater.inflate(R.layout.message_receive, null)));
                editText.setText("");//clear edit text
                adapter.notifyDataSetChanged();//update adapter
            }
        });

        listView.setOnItemClickListener((adapterView, view, position, id)-> {
            new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete this message?")
                .setMessage("The Selected Row is: " + position + "\nThe Database Id is: " + id)
                .setPositiveButton("yes", (dialog, which) -> {
                    listItems.remove(position);
                    adapter.notifyDataSetChanged();
                }).setNegativeButton("no", null)
                    .show();
        });


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

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private TextMessage(int image, String text, View view) {
            this.image = image;
            this.text = text;
            this.view = view;
        }


    }
}
