package com.gt.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private static final String JOKE_EXTRA = "JOKE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();
        String joke = null;
        if (intent.hasExtra(JOKE_EXTRA)) {
            joke = intent.getStringExtra(JOKE_EXTRA);
        }
        TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
        if (joke != null) {
            jokeTextView.setText(joke);
        }
    }
}
