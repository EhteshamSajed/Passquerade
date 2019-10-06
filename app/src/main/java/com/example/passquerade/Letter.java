package com.example.passquerade;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Letter {
    private String character;
    private Bitmap fontBitmap;
    private String fontEffect;
    private Rect fontRect;


    public Letter(){
        //  extract currentFont bitmap upon init
    }

    public Letter(String character, Rect fontRect) {
        this.character = character;
        this.fontRect = fontRect;
    }

    public Letter(String character, Rect fontRect, String fontEffect) {
        this.character = character;
        this.fontEffect = fontEffect;
        this.fontRect = fontRect;
    }

    public Letter(String character, Rect fontRect, String fontEffect, Bitmap fontBitmap) {
        this.character = character;
        this.fontBitmap = fontBitmap;
        this.fontEffect = fontEffect;
        this.fontRect = fontRect;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Bitmap getFontBitmap() {
        return fontBitmap;
    }

    public void setFontBitmap(Bitmap fontBitmap) {
        this.fontBitmap = fontBitmap;
    }

    public String getFontEffect() {
        return fontEffect;
    }

    public void setFontEffect(String fontEffect) {
        this.fontEffect = fontEffect;
    }

    public Rect getFontRect() {
        return fontRect;
    }

    public void setFontRect(Rect fontRect) {
        this.fontRect = fontRect;
    }
}
