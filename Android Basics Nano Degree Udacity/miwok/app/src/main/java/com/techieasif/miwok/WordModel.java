package com.techieasif.miwok;

public class WordModel {
    private String miwokTranslation;
    private String englishTranslation;
    private int audioResource;
    private int imageResource = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public WordModel(String miwokTranslation, String englishTranslation, int audioResource) {
        this.miwokTranslation = miwokTranslation;
        this.englishTranslation = englishTranslation;
        this.audioResource = audioResource;
    }

    public WordModel(String miwokTranslation, String englishTranslation, int imageResource, int audioResource) {
        this.miwokTranslation = miwokTranslation;
        this.englishTranslation = englishTranslation;
        this.imageResource = imageResource;
        this.audioResource = audioResource;
    }

    public int getAudioResource() {
        return audioResource;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public int getImageResource() {
        return imageResource;
    }
public  boolean hasImage(){
        return imageResource != NO_IMAGE_PROVIDED;
}

    @Override
    public String toString() {
        return "WordModel{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", englishTranslation='" + englishTranslation + '\'' +
                ", audioResource=" + audioResource +
                ", imageResource=" + imageResource +
                '}';
    }
}
