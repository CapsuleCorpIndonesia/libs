package lib.imb.c.login;

import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by korneliussendy on 8/22/17.
 */

public class AuthSetup {

    private ArrayList<AuthUI.IdpConfig> idpConfigList = new ArrayList<>();
    public static final boolean LOG_IN_FIRST = true;
    public static final String EMAIL_PROVIDER = AuthUI.EMAIL_PROVIDER;
    public static final String PHONE_VERIFICATION_PROVIDER = AuthUI.PHONE_VERIFICATION_PROVIDER;
    public static final String GOOGLE_PROVIDER = AuthUI.GOOGLE_PROVIDER;
    public static final String FACEBOOK_PROVIDER = AuthUI.FACEBOOK_PROVIDER;
    public static final String TWITTER_PROVIDER = AuthUI.TWITTER_PROVIDER;

    private String tosUrl = "";
    private String privacyUrl = "";

    private static final String[] LIST_PROVIDERS = {
            EMAIL_PROVIDER,
            PHONE_VERIFICATION_PROVIDER,
            GOOGLE_PROVIDER,
            FACEBOOK_PROVIDER,
            TWITTER_PROVIDER
    };

    public AuthSetup addProvider(AuthUI.IdpConfig provider) {
        if (idpConfigList == null)
            idpConfigList = new ArrayList<>();
        idpConfigList.add(provider);
        return this;
    }

    public AuthSetup addProvider(String provider) {
        if (!Arrays.asList(LIST_PROVIDERS).contains(provider)) {
            throw new NoSuchElementException("Unknown List Provider");
        }

//        AuthUI.IdpConfig idp = new AuthUI.IdpConfig.Builder(provider).build();
//        if (!idpConfigList.contains(idp))
//            idpConfigList.add(idp);

        idpConfigList.add(new AuthUI.IdpConfig.Builder(provider).build());
        return this;
    }

    public AuthSetup addProviders(String[] providers) {
        for (String provider : providers) {
            addProvider(provider);
        }
        return this;
    }

    public AuthSetup addProviders(List<String> providers) {
        for (String provider : providers) {
            addProvider(provider);
        }
        return this;
    }

    public AuthSetup addProviders(ArrayList<AuthUI.IdpConfig> idpConfigList) {
        this.idpConfigList.addAll(idpConfigList);
        return this;
    }

//    public AuthSetup addProviders(List<AuthUI.IdpConfig> idpConfigList) {
//        this.idpConfigList.addAll(idpConfigList);
//        return this;
//    }

    public ArrayList<AuthUI.IdpConfig> getProviders() {
        return idpConfigList;
    }

    public String getTosUrl() {
        return tosUrl;
    }

    public void setTosUrl(String tosUrl) {
        this.tosUrl = tosUrl;
    }

    public String getPrivacyUrl() {
        return privacyUrl;
    }

    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }
}
