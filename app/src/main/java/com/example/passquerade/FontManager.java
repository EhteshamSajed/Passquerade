package com.example.passquerade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FontManager {
    private static final String TAG = "FontManager";
    public static FontManager instance;

    public ArrayList<String> fontName = new ArrayList<String>();
    private ArrayList<Bitmap> fontSprites = new ArrayList<Bitmap>();
    private HashMap<String, Font> fontHashMap = new HashMap<String, Font>();
    private Context context;



    public FontManager(Context context){
        FontManager.instance = this;
        this.context = context;

        final BitmapFactory.Options opt = new BitmapFactory.Options();
        //opt.inJustDecodeBounds = true;
        opt.inScaled = false;

        fontName.add("Blur");
        fontSprites.add(BitmapFactory.decodeResource( context.getResources(), R.drawable.original_v2_blur_22, opt));

        fontName.add("Colour Halftone");
        fontSprites.add(BitmapFactory.decodeResource( context.getResources(), R.drawable.font_color_halftone_20, opt));

        fontName.add("Mosaic");
        fontSprites.add(BitmapFactory.decodeResource( context.getResources(), R.drawable.font_mossaic_27, opt));

        fontName.add("Crystallize");
        fontSprites.add(BitmapFactory.decodeResource( context.getResources(), R.drawable.font_crystallize_20, opt));

    }

    public Font getFontInstance(String _fontName){
        Log.d(TAG, "getFontInstance: searching font name: " + _fontName);
        if(!fontHashMap.containsKey(_fontName)) {
            //  load the currentFont and add...

            int index = fontName.indexOf(_fontName);


            Log.d(TAG, "getFontInstance: loading new font at " + index);

            Font font = new Font(fontSprites.get(index), FontManager.readAssetFile("alphabet.json", context), _fontName);
            fontHashMap.put(_fontName, font);
        }

        return fontHashMap.get(_fontName);
    }

    public static String readAssetFile(String fileName, Context context){
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
