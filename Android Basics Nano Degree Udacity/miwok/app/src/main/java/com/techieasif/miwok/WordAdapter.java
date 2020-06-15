package com.techieasif.miwok;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<WordModel> {

    public WordAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public WordAdapter(Activity context, ArrayList<WordModel> wordModels) {
        super(context, 0, wordModels);
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
        final TextView englishText = (TextView) listItemView.findViewById(R.id.englishText);
        englishText.setText(wordModel.getEnglishTranslation());
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), miwokText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return listItemView;
    }
}
