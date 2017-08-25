package lib.imb.demo;

import android.support.multidex.MultiDexApplication;

import java.util.Arrays;

import lib.imb.c.login.AuthSetup;
import lib.imb.c.login.IMBAuth;

/**
 * Created by korneliussendy on 8/23/17.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        IMBAuth.getInstance().init(
                new AuthSetup().addProviders(
                        Arrays.asList(
                                AuthSetup.EMAIL_PROVIDER,
                                AuthSetup.PHONE_VERIFICATION_PROVIDER,
                                AuthSetup.GOOGLE_PROVIDER
//                                AuthSetup.FACEBOOK_PROVIDER,
//                                AuthSetup.TWITTER_PROVIDER
                        )
                )
        );
    }
}
