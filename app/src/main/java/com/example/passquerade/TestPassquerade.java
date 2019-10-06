package com.example.passquerade;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TestPassquerade extends AppCompatActivity {
    private static final String TAG = "TestPassquerade";

    int displayHeight;
    int displayWidth;

    EditText passwordText;
    Spinner fontSpinner;
    Font currentFont;
    LinearLayout lineaLayout;

    //RelativeLayout lineaLayout;
    FontManager fontManager;
    Random random = new Random();

    String selectedText = "";

    int numOfTrials = 0;
    int numOfMatches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_passquerade);

        fontManager = new FontManager(this);

        configureUI();

        populateFontList();
        addListenerOnSpinnerItemSelection();
        addTextChangedListener();
    }

    String pickRandomString(){
        String fontToRender = "";
        if(MainActivity.passTextList.size() > 0)
            fontToRender = MainActivity.passTextList.get(random.nextInt(MainActivity.passTextList.size()));

        return fontToRender;
    }

    public void renderFont(String text){
        lineaLayout.removeAllViews();

        Bitmap[] bitmaps = getResizedBitmaps(text);

        for(int i = 0; i < bitmaps.length; i++){
            ImageView imageView = createDynamicImageView();
            imageView.setImageBitmap(bitmaps[i]);
        }
    }

    Bitmap[] getResizedBitmaps(String text){
        Bitmap[] bitmaps = new Bitmap[text.length()];
        int sizeX = 0;
        int sizeY = 0;

        float factor = 1;

        //  get total bitmap size
        for(int i = 0; i < text.length(); i++){
            sizeX += currentFont.getLetter(text.charAt(i) + "").getFontRect().right;
            sizeY += currentFont.getLetter(text.charAt(i) + "").getFontRect().bottom;
        }

        if(sizeX > displayWidth){
            factor = (float) displayWidth/sizeX;
        }

        Log.d(TAG, "getResizedBitmaps: factor = " + factor);

        for(int i = 0; i < text.length(); i++){
            Bitmap fontBitmap = currentFont.getLetterBitmap(this, text.charAt(i) + "");
            bitmaps[i] = Bitmap.createScaledBitmap(fontBitmap, (int)(fontBitmap.getWidth()*factor), (int)(fontBitmap.getHeight()*factor), true);
        }

        return bitmaps;
    }

    public ImageView createDynamicImageView(){
        ImageView imageView = new ImageView(this);

        lineaLayout.addView(imageView);

        return imageView;
    }

    void populateFontList(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fontManager.fontName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(dataAdapter);
    }

    void onPressSubmitButton(){
        numOfTrials++;

        if(selectedText.equals(passwordText.getText().toString())){
            numOfMatches++;
        }

        passwordText.setText("", TextView.BufferType.EDITABLE);
        selectedText = pickRandomString();
        renderFont(selectedText);
    }

    void onPressShowResultButton(){
        AlertDialog.Builder resultDialogBuilder = new AlertDialog.Builder(this);
        View resultDialogView = getLayoutInflater().inflate(R.layout.result_dialog, null);

        TextView totalTrialText = (TextView) resultDialogView.findViewById(R.id.totalTrialText);
        TextView numOfCorrectText = (TextView) resultDialogView.findViewById(R.id.numOfCorrectText);
        TextView numOfIncorrectText = (TextView) resultDialogView.findViewById(R.id.numOfIncorrectText);

        totalTrialText.setText("Number of trials: " + numOfTrials);
        numOfCorrectText.setText("Number of correct matches: " + numOfMatches);
        numOfIncorrectText.setText("Number of incorrect matches: " + (numOfTrials - numOfMatches));

        resultDialogBuilder.setView(resultDialogView);
        final AlertDialog resultDialog = resultDialogBuilder.create();

        Button tryAgainButton = (Button) resultDialogView.findViewById(R.id.tryAgainButton);
        Button homeButton = (Button) resultDialogView.findViewById(R.id.homeButton);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(TestPassquerade.this, TestPassquerade.class));
                numOfMatches = numOfTrials = 0;
                passwordText.setText("", TextView.BufferType.EDITABLE);
                selectedText = pickRandomString();
                renderFont(selectedText);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.cancel();
                MainActivity.passTextList.removeAll(MainActivity.passTextList);
                finish();
            }
        });

        resultDialog.show();
    }

    public void addListenerOnSpinnerItemSelection() {
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFont = FontManager.instance.getFontInstance(parent.getItemAtPosition(position).toString());
                //currentFont.getFontName();

                /*Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();*/
                
                if(currentFont == null)
                    Log.d(TAG, "onItemSelected: current font null");

                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + currentFont.getFontName(),
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onItemSelected: currentFont init");

                selectedText = pickRandomString();
                renderFont(selectedText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void addTextChangedListener(){
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("test", "text : " + passwordText.getText().toString());

                /*Log.d("test", "text : " + s);
                Log.d("test", "start : " + start + " before: " + before + " count: " + count);

                renderFont(s+"");*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void configureUI(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

        lineaLayout = (LinearLayout) findViewById(R.id.linearLayout);
        passwordText = (EditText) findViewById(R.id.passwordText);
        fontSpinner = (Spinner) findViewById(R.id.fontSpinner);
        Button newTestButton = (Button) findViewById(R.id.showResultButton);
        Button submitButton = (Button) findViewById(R.id.submitButton);


        newTestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: showResultButton");
                onPressShowResultButton();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: submitButton");
                onPressSubmitButton();
            }
        });
    }
}