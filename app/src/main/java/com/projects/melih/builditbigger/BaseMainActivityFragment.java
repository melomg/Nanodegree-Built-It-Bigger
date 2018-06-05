package com.projects.melih.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.melih.jokedisplayer.JokeActivity;

public class BaseMainActivityFragment extends Fragment implements View.OnClickListener {
    protected Context context;
    private ContentLoadingProgressBar progressBar;
    private AsyncTask<Void, Void, Result> jokesAsyncTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = root.findViewById(R.id.progressBar);

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

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}