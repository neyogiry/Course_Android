package neyo.demo.tipcal;

import android.app.Application;

/**
 * Created by Neyo
 */
public class TipCalApp extends Application {
    private final static String ABOUT_URL = "https://github.com/neyogiry";

    public String getAboutUrl() {
        return ABOUT_URL;
    }
}
