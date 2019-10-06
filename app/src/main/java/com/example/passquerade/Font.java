package com.example.passquerade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Font {
    Context context;
    public HashMap<String, Letter> fontHashMap = new HashMap<String, Letter>();

    private Bitmap fontSpriteSheet;
    private String jsonString;
    private String fontName;
    JSONObject jsonObject;

    public Font(/*Context context,*/ Bitmap fontSpriteSheet, String jsonString, String fontName) {
        this.fontSpriteSheet = fontSpriteSheet;
        this.jsonString = jsonString;
        this.fontName = fontName;
        //this.context = context;

        try {
            jsonObject = new JSONObject(jsonString);
            extractFonts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getFontSpriteSheet() {
        return fontSpriteSheet;
    }

    public void setFontSpriteSheet(Bitmap fontSpriteSheet) {
        this.fontSpriteSheet = fontSpriteSheet;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public void extractFonts(){
        try {
            JSONArray alphabets = jsonObject.getJSONArray("alphabet");

            for (int i = 0; i < alphabets.length(); i++){
                JSONObject alphabet = alphabets.getJSONObject(i);

                String letterString = alphabet.getString("letter");
                int left = alphabet.getInt("left");
                int top = alphabet.getInt("top");
                int width = alphabet.getInt("width");
                int height = alphabet.getInt("height");

                Log.d("JSON ", letterString + " " + left + " " + top + " " + width + " " + height);

                /*final BitmapFactory.Options opt = new BitmapFactory.Options();
                //opt.inJustDecodeBounds = true;
                opt.inScaled = false;
                Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.original_v2_blur_22, opt);

                Bitmap letterBitmap = Bitmap.createBitmap(bitmap, left, top, displayWidth, displayHeight);*/
                Bitmap letterBitmap = Bitmap.createBitmap(fontSpriteSheet, left, top, width, height);

                Letter letter = new Letter(letterString, new Rect(left,top, width,height), this.fontName, letterBitmap);

                fontHashMap.put(letterString, letter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getLetterBitmap(Context context, String s){
        Bitmap letterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_character);
        if(fontHashMap.containsKey(s))
            letterBitmap = fontHashMap.get(s).getFontBitmap();

        return letterBitmap;
    }

    public Letter getLetter(String s){
        if(fontHashMap.containsKey(s))
            return fontHashMap.get(s);
        return null;
    }
}
