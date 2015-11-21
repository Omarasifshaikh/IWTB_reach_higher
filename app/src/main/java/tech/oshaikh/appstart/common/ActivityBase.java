package tech.oshaikh.appstart.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by omar on 11/21/15.
 */
public class ActivityBase extends FragmentActivity {
    public static final String TAG = "ActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
    }
}
