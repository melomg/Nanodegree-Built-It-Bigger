package com.projects.melih.builditbigger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.projects.melih.builditbigger.base.BaseFragment;
import com.projects.melih.jokedisplayer.JokeActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment implements View.OnClickListener {

    private ContentLoadingProgressBar progressBar;
    private AsyncTask<Void, Void, Result> jokesAsyncTask;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = root.findViewById(R.id.progressBar);

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        root.findViewById(R.id.tellJoke).setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelJokesTask();
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.show();
        cancelJokesTask();
        jokesAsyncTask = new JokesAsyncTask(new JokesAsyncTask.Callback() {
            @Override
            public void onSuccess(String joke) {
                progressBar.hide();
                startActivity(JokeActivity.newIntent(context, joke));
            }

            @Override
            public void onFailed(String error) {
                progressBar.hide();
                showToast(error);
            }

            @Override
            public void onCancelled() {
                progressBar.hide();
                showToast(context.getString(R.string.jokes_cancelled));
            }
        }).execute();
    }

    private void cancelJokesTask() {
        if (jokesAsyncTask != null) {
            jokesAsyncTask.cancel(true);
        }
    }
}