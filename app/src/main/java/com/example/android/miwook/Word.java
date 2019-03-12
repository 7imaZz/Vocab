package com.example.android.miwook;

public class Word {
    private String miwokTranslation, defaultTranslation;
    private int imgId = NO_IMAGE;
    private int id;
    private final static int NO_IMAGE = -1;

    public Word(String miwokTranslation, String defaultTranslation, int imgId) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imgId = imgId;
    }


    public Word(String miwokTranslation, String defaultTranslation, int imgId, int id) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imgId = imgId;
        this.id = id;
    }

    public Word(String miwokTranslation, String defaultTranslation) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public int getImgId() {
        return imgId;
    }

    public boolean hasImage(){
        return imgId != NO_IMAGE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
