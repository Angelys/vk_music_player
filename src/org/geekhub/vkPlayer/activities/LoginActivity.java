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
	
	final String LOG_TAG = "myLogs";
    private static final String TAG = "LoginActivity";

    private static final String SETTINGS = "10";//Friends(2) + Audio(8)

    
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- LoginActivity - onCreate --- ");
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
        Log.d(LOG_TAG, "--- LoginActivity - onCreate --- url = " + url);
        webview.loadUrl(url);
    }

    class VkontakteWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        	Log.d(LOG_TAG, "--- VkontakteWebViewClient - onPageStarted --- ");
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }

    private void parseUrl(String url) {
    	Log.d(LOG_TAG, "--- LoginActivity - parseUrl(url) --- ");
        try {
            if(url==null){
            	Log.d(LOG_TAG, "--- LoginActivity - parseUrl(url) --- (url==null) - go RETURN");
                return;
            }
            Log.i(TAG, "url=" + url);
            if(url.startsWith(Auth.redirect_url)){
            	Log.d(LOG_TAG, "--- LoginActivity - parseUrl(url) - (url.startsWith(Auth.redirect_url - " + Auth.redirect_url);
                if(!url.contains("error=")){
                	Log.d(LOG_TAG, "--- LoginActivity - parseUrl(url) -url.startsWith <redirect_url> - !url.contains(error=)");
                    String[] auth=Auth.parseRedirectUrl(url);
                    Intent intent=new Intent();
                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));
                    Log.d(LOG_TAG, "--- LoginActivity - parseUrl(url) -url.startsWith - setResult(Activity.RESULT_OK");
                    setResult(Activity.RESULT_OK, intent);
                }
                Log.d(LOG_TAG, "--- LoginActivity - finish()");
                finish();
            }
        } catch (Exception e) {
        	Log.d(LOG_TAG, "--- LoginActivity - parseUrl(String url) - catch (Exception e)");
            e.printStackTrace();
        }
    }
}