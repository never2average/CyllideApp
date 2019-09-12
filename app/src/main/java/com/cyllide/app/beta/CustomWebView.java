package com.cyllide.app.beta;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pl.droidsonroids.gif.GifImageView;

public class CustomWebView extends WebViewClient {

    private GifImageView spinner;
    private WebView webview;
    String ShowOrHideWebViewInitialUse = "show";

    public CustomWebView(GifImageView spinner, WebView webview) {
        this.spinner = spinner;
        this.webview = webview;
    }

    @Override
    public void onPageStarted(WebView webview, String url, Bitmap favicon) {
        webview.setVisibility(webview.INVISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        spinner.setVisibility(View.INVISIBLE);

        view.setVisibility(webview.VISIBLE);
        super.onPageFinished(view, url);

    }

}
