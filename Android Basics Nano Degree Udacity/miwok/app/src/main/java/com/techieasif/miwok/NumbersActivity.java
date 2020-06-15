package com.techieasif.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        ArrayList<WordModel> words = new ArrayList<WordModel>();
       words.add(new WordModel("lutti", "One"));
       words.add(new WordModel("otiiko", "Two"));
       words.add(new WordModel("tolookosu", "Three"));
       words.add(new WordModel("oyyisa", "Four"));
       words.add(new WordModel("massoka", "Five"));
       words.add(new WordModel("temmoka", "Six"));
       words.add(new WordModel("keneka", "Seven"));
       words.add(new WordModel("kawinta", "Eight"));
       words.add(new WordModel("wo'e", "Nine"));
       words.add(new WordModel("na'aacha", "Ten"));

        WordAdapter itemsAdapter =
                new WordAdapter(this, words);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
    }


}