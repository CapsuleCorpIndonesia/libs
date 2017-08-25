package lib.imb.c.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

/**
 * Created by korneliussendy on 8/22/17.
 */

public class AuthSettings {


    private FragmentActivity activity;
    private IMBAuth.OnAuthListener onAuthListener;


    public AuthSettings(@NonNull FragmentActivity activity, IMBAuth.OnAuthListener onAuthListener) {
        this.activity = activity;
        this.onAuthListener = onAuthListener;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public IMBAuth.OnAuthListener getOnAuthListener() {
        return onAuthListener;
    }

    public void setOnAuthListener(IMBAuth.OnAuthListener onAuthListener) {
        this.onAuthListener = onAuthListener;
    }
}
