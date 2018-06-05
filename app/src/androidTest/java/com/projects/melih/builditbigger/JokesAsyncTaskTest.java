package com.projects.melih.builditbigger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

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

        assertThat(newJoke, not(isEmptyOrNullString()));
    }
}