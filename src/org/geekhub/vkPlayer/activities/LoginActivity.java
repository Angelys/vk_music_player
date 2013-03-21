package org.geekhub.vkPlayer.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.perm.kate.api.Auth;
import org.geekhub.vkPlayer.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import org.geekhub.vkPlayer.utils.Constants;


public class LoginActivity extends FragmentActivity {
    private static final String TAG = "LoginActivity";

    private static final String SETTINGS = "10";//Friends(2) + Audio(8)

    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        webview = (WebView) findViewById(R.id.vkview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);

        //To receive notification of the end of the page load
        webview.setWebViewClient(new VkontakteWebViewClient());

        //otherwise CookieManager will fall with java.lang.IllegalStateException: CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()
        CookieSyncManager.createInstance(this);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        String url=Auth.getUrl(Constants.API_ID, SETTINGS);
        webview.loadUrl(url);
    }

    class VkontakteWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }

    private void parseUrl(String url) {
        try {
            if(url==null)
                return;
            Log.i(TAG, "url=" + url);
            if(url.startsWith(Auth.redirect_url))
            {
                if(!url.contains("error=")){
                    String[] auth=Auth.parseRedirectUrl(url);
                    Intent intent=new Intent();
                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));
                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}