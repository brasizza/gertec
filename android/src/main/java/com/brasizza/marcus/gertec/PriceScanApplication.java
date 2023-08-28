package com.brasizza.marcus.gertec;

import android.app.Application;
import android.content.Context;

public class PriceScanApplication extends Application {

    private static Context context;


    public static void setContext(Context flutterContext) {
        context = flutterContext;
    }
    public static Context getContext() {
        return context;
    }
}
