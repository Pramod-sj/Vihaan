package registrationform.com.registrationform;

/**
 * Created by pramo on 10/4/2017.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static registrationform.com.registrationform.R.id.container;
import static registrationform.com.registrationform.R.layout.about;

public class home_activity extends Fragment{
    WebView webView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webView);
        webView.loadUrl("http://google.com");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());
        return view;
    }



}
