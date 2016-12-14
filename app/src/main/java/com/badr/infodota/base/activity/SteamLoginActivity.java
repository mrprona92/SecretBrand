package com.badr.infodota.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.badr.infodota.R;

/**
 * Created by ABadretdinov
 * 13.10.2015
 * 14:05
 */
public class SteamLoginActivity extends Activity {
    public static final String USER_ID = "user_id";
    public static final String REALM_PARAM = "infodota";
    public static final String LOGIN_URL = "https://steamcommunity.com/openid/login?" +
            "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.mode=checkid_setup&" +
            "openid.ns=http://specs.openid.net/auth/2.0&" +
            "openid.realm=http://" + REALM_PARAM + "&" +
            "openid.return_to=http://" + REALM_PARAM + "/signin/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steam_login_layout);

        final WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {

                //checks the url being loaded
                setTitle(url);
                Uri Url = Uri.parse(url);

                if (Url.getAuthority().equals(REALM_PARAM.toLowerCase())) {
                    // That means that authentication is finished and the url contains user's id.
                    webView.stopLoading();

                    // Extracts user id.
                    Uri userAccountUrl = Uri.parse(Url.getQueryParameter("openid.identity"));
                    String userId = userAccountUrl.getLastPathSegment();
                    Intent intent = getIntent();
                    intent.putExtra(USER_ID, userId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        // Constructing openid url request
        webView.loadUrl(LOGIN_URL);
    }
}
