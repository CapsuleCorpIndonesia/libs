package lib.imb.c.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.app.Activity.RESULT_OK;

/**
 * Created by korneliussendy on 8/22/17.
 */

public final class IMBAuth {

    private static final int RC_SIGN_IN = 1168;
    public static volatile IMBAuth auth;
    private AuthSetup authSetup;
    private AuthSettings authSettings;

    public int ERROR_NO_RESPONSE = -1;
    public int ERROR_UNKNOWN_RESPONSE = -2;
    public int ERROR_NO_NETWORK = ErrorCodes.NO_NETWORK;
    public int ERROR_UNKNOWN = ErrorCodes.UNKNOWN_ERROR;

    private IMBAuth() {
        if (auth != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static IMBAuth getInstance() {
        if (auth == null) {
            synchronized (IMBAuth.class) {
                if (auth == null)
                    auth = new IMBAuth();
            }
        }
        return auth;
    }

    //Make singleton from serialize and deserialize operation.
    protected IMBAuth readResolve() {
        return getInstance();
    }

    public IMBAuth init(@NonNull AuthSetup authSetup) {
        if (auth == null) {
            throw new NullPointerException("Use getInstance() method to begin");
        }

        this.authSetup = authSetup;

        return this;
    }

    public void authenticate(AuthSettings authSettings) {
        if (authSettings == null)
            throw new NullPointerException("Auth setup required");
        if (authSettings.getActivity() == null)
            throw new NullPointerException("unable to find activity");

        this.authSettings = authSettings;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            authSettings.getOnAuthListener().onAlreadyLogged(user);
            return;
        }

        AuthUI.SignInIntentBuilder intentBuilder = AuthUI.getInstance().createSignInIntentBuilder();
        intentBuilder.setAvailableProviders(authSetup.getProviders());

        if (!isEmpty(authSetup.getTosUrl()))
            intentBuilder.setTosUrl(authSetup.getTosUrl());

        if (!isEmpty(authSetup.getPrivacyUrl()))
            intentBuilder.setPrivacyPolicyUrl(authSetup.getPrivacyUrl());

        authSettings.getActivity().startActivityForResult(
                // Get an instance of AuthUI based on the default app
                AuthUI.getInstance().createSignInIntentBuilder().build(),
                RC_SIGN_IN);

    }

    public void logout(FragmentActivity activity, final OnLogoutListener onLogoutListener) {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        onLogoutListener.onSuccess();
                    }
                });

    }

    /*
    INTERFACE
     */
    public interface OnAuthListener {
        //called when auth success
        boolean onSuccess(IdpResponse response);

        boolean onFailed(int errorCode);

        boolean onAlreadyLogged(FirebaseUser user);
    }

    interface OnLogoutListener {
        //called when logout success
        boolean onSuccess();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Log.e(getClass().getSimpleName(), "Result OK");
                authSettings.getOnAuthListener().onSuccess(response);
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    Log.e(getClass().getSimpleName(), "No Response");
                    authSettings.getOnAuthListener().onFailed(ERROR_NO_RESPONSE);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e(getClass().getSimpleName(), "No Network");
                    authSettings.getOnAuthListener().onFailed(ERROR_NO_NETWORK);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e(getClass().getSimpleName(), "Unknown Error");
                    authSettings.getOnAuthListener().onFailed(ERROR_UNKNOWN);
                    return;
                }
            }

            authSettings.getOnAuthListener().onFailed(ERROR_UNKNOWN_RESPONSE);
            Log.e(getClass().getSimpleName(), "Unknown Response");
        }

    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }
}