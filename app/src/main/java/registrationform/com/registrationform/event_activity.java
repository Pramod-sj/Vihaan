package registrationform.com.registrationform;
/**
 * Created by pramo on 10/4/2017.
 */
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.R.attr.width;
import static registrationform.com.registrationform.R.id.container;
import static registrationform.com.registrationform.R.layout.about;
public class event_activity extends Fragment{
    WebView webView;
    View view;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.event, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.pg1);
        webView = (WebView)view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.vesvihaan.com/Events");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        //Keep in mind that by doing this the webview will not update immediately every page if anything changes but decrease loading time.
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setEnableSmoothTransition(true);
        /*String html = "<html><head></head>";
        html += "<body></body></html>";
        webView.loadDataWithBaseURL("file:///android_asset/resize.html",html,"text/html","UTF-8",null);*/
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/nointernet.html");

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }


}
