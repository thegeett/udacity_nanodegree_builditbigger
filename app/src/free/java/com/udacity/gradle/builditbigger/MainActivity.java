package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity {

    private static final String JOKE_EXTRA = "JOKE_EXTRA";

    private Context mContext;
    // instance variable for Interstitial Ad
    private InterstitialAd mInterstitialAd;
    private ProgressBar mJokeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mJokeProgressBar = (ProgressBar) findViewById(R.id.joke_progress_bar);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startTask();
            }
        });

        requestNewInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startTask();
        }

        /*ProvideJokes provideJokes = new ProvideJokes();
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JOKE_EXTRA, provideJokes.getJoke());
        startActivity(intent);*/
        //Toast.makeText(this, provideJokes.getJoke(), Toast.LENGTH_SHORT).show();
    }

    /**
     *  make progress bar visible as soon sa we call joke asynctask.
     *
     */

    private void startTask(){
        mJokeProgressBar.setVisibility(View.VISIBLE);
        new JokeAsyncTask().execute(this, mJokeProgressBar);
    }

    /*
    *  This method will build and load ad, and can be use when right time come.
    * */
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


}
