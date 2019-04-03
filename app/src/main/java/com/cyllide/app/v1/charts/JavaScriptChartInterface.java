package com.cyllide.app.v1.charts;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class JavaScriptChartInterface {
    Context context;
    String ticker;
    String frequency;

    public JavaScriptChartInterface(Context context, String ticker, String frequency) {
        this.context = context;
        this.ticker = ticker;
        this.frequency = frequency;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @JavascriptInterface
    public String[] getFromAndroid(){
        String value[] = {ticker,frequency};
        return value;
    }
}
