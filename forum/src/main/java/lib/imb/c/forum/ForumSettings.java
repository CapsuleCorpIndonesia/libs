package lib.imb.c.forum;

import android.support.annotation.IdRes;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.concurrent.CompletableFuture;

/**
 * Created by korneliussendy on 8/31/17.
 */

public final class ForumSettings {

    public static volatile ForumSettings settings;

    private static boolean allowFeatured;
    private static boolean allowSticky;
    private static int featuredQuantityLimit;

    @IdRes
    private static int defaultRemoteConfig = R.xml.remote_config_defaults;

    private FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();

    private ForumSettings() {
        if (settings != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
    }

    public static ForumSettings init() {
        if (settings == null) {
            synchronized (ForumSettings.class) {
                if (settings == null)
                    settings = new ForumSettings();
            }
        }
        setupValue();
        return settings;
    }

    private static void setupValue() {

    }

    private static void test() {

    }


    //Make singleton from serialize and deserialize operation.
    protected ForumSettings readResolve() {
        return init();
    }


}
