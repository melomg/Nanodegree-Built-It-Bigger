package com.projects.melih.builditbigger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class JokesAsyncTaskTest {
    private JokesAsyncTask jokesAsyncTask;
    private String newJoke;
    private CountDownLatch latch;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jokesAsyncTask = new JokesAsyncTask(new JokesAsyncTask.Callback() {
            @Override
            public void onSuccess(String joke) {
                newJoke = joke;
                latch.countDown();
            }

            @Override
            public void onFailed(String error) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    @Test
    public void jokeIsNotEmpty() throws InterruptedException {
        latch = new CountDownLatch(1);

        jokesAsyncTask.execute();
        latch.await();

        verify(newJoke).contains(anyString());
    }
}