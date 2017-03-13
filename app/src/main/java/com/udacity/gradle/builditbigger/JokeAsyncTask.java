package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.gt.myapplication.backend.myApi.MyApi;
import com.gt.mylibrary.JokeActivity;

import java.io.IOException;

/**
 * Created by geetthaker on 2/21/17.
 */

public class JokeAsyncTask extends AsyncTask<Object, Void, String> {
    private static final String JOKE_EXTRA = "JOKE_EXTRA";
    private static MyApi myApiService = null;
    private Context context;
    private ProgressBar mProgressBar;

    @Override
    protected String doInBackground(Object... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("http://192.168.1.103:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = (Context) params[0];
        mProgressBar = (ProgressBar) params[1];
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JOKE_EXTRA, result);
        context.startActivity(intent);
    }
}