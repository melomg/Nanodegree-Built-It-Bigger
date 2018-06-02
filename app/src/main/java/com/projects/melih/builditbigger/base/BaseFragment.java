package com.projects.melih.builditbigger.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
