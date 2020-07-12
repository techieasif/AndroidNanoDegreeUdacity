package com.techieasif.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<WordModel> {
    private int backgroundColor;
//    private MediaPlayer mediaPlayer;

    public WordAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public WordAdapter(Activity context, ArrayList<WordModel> wordModels, int backgroundColor) {
        super(context, 0, wordModels);
        this.backgroundColor = backgroundColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        WordModel wordModel = getItem(position);

        final TextView miwokText = (TextView) listItemView.findViewById(R.id.miwokText);
        miwokText.setText(wordModel.getMiwokTranslation());
        final ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        //setting image to ImageView
        final TextView englishText = (TextView) listItemView.findViewById(R.id.englishText);
        /*
        * if image provide then it will be set to image view , else it will hide
        * */
        if (wordModel.hasImage()) {
            imageView.setImageResource(wordModel.getImageResource());
        } else {
            imageView.setVisibility(View.GONE);
        }
        englishText.setText(wordModel.getEnglishTranslation());

        View textContainer = (View) listItemView.findViewById(R.id.textContainer);
        int color = ContextCompat.getColor(getContext(), backgroundColor);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
