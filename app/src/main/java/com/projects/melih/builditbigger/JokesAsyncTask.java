package com.projects.melih.builditbigger;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokesAsyncTask extends AsyncTask<Void, Void, Result> {
    private static MyApi apiService = null;
    private final Callback callback;

    public JokesAsyncTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Result doInBackground(Void... voids) {
        if (apiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            apiService = builder.build();
        }

        try {
            return new Result(apiService.jokes().execute().getData(), null);
        } catch (IOException e) {
            return new Result(null, e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        final String error = result.getError();
        if (TextUtils.isEmpty(error)) {
            callback.onSuccess(result.getData());
        } else {
            callback.onFailed(error);
        }
    }

    @Override
    protected void onCancelled(Result result) {
        super.onCancelled(result);
        callback.onCancelled();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onCancelled();
    }

    public interface Callback {
        void onSuccess(String joke);

        void onFailed(String error);

        void onCancelled();
    }
}