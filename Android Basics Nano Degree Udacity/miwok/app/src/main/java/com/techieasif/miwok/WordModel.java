package com.techieasif.miwok;

public class WordModel {
    private String miwokTranslation;
    private String englishTranslation;

    public WordModel(String miwokTranslation, String englishTranslation) {
        this.miwokTranslation = miwokTranslation;
        this.englishTranslation = englishTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }
}
