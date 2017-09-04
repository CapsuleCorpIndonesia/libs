package lib.imb.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import lib.imb.c.login.AuthSettings;
import lib.imb.c.login.IMBAuth;
import lib.imb.c.mapgeo.MapsGeo;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMBAuth.getInstance().authenticate(
                new AuthSettings(this, new IMBAuth.OnAuthListener() {
                    @Override
                    public boolean onSuccess(IdpResponse response) {
                        Log.e("AUTH", "Success");
                        return false;
                    }

                    @Override
                    public boolean onFailed(int errorCode) {
                        Log.e("AUTH", "Failed");
                        return false;
                    }

                    @Override
                    public boolean onAlreadyLogged(FirebaseUser user) {
                        Log.e("AUTH", "Logged");
                        return false;
                    }
                })
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IMBAuth.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
