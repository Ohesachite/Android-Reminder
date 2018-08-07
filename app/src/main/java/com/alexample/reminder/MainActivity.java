package com.alexample.reminder;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.*;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String logTag = "logTag";
    private static final String descTag = "descTag";
    private static ArrayList<String> Description = new ArrayList<String>();
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_ARRAY = "extra_array";
    private static boolean addingNewReminder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.ree);

        if(addingNewReminder) {
            Intent intent = getIntent();

            Description = intent.getStringArrayListExtra(EXTRA_ARRAY);
            for (int i = 0; i < Description.size(); i++) {
                Log.i(logTag, Description.get(i));
            }
        }

        addingNewReminder = false;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Description);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CharSequence[] options = new CharSequence[]{"Delete", "Cancel"};
                final int pos = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Reminder?");
                builder.setItems(options, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        if(which == 0) {
                            Description.remove(pos);
                            recreate();
                            }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(logTag, "Bundle saved");
        savedInstanceState.putStringArrayList("descTag", Description);
    }

    public void setNewReminder(View v){
        addingNewReminder = true;
        Intent intent = new Intent(this, NewReminderActivity.class);
        String message = "New Reminder!";
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_ARRAY, Description);
        startActivity(intent);
    }
}
