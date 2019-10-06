package com.example.passquerade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static ArrayList<String> passTextList = new ArrayList<>();

    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureUI();
        //setDummyTexts();
        initRecyclerView();
    }

    private void setDummyTexts(){
        passTextList.add("Sajed");
        passTextList.add("Arup");
        passTextList.add("Tom");
        passTextList.add("Jim");
    }

    void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(this, passTextList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void configureUI(){
        Button newTestButton = (Button) findViewById(R.id.showResultButton);
        Button addTextButton = (Button) findViewById(R.id.addTextButton);

        final EditText editText = (EditText) findViewById(R.id.editText);

        newTestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestPassquerade.class));
            }
        });

        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passText = editText.getText().toString();
                if(passText.length() != 0) {
                    passTextList.add(passText);
                    recyclerViewAdapter.notifyDataSetChanged();

                    editText.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });
    }

    public void removeItem(int position){
        Log.d(TAG, "removing item at :" + position);
        passTextList.remove(position);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
