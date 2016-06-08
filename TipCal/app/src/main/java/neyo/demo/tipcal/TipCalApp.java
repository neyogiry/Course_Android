package neyo.demo.tipcal;

import android.app.Application;

/**
 * Created by Jose Fernando on 05/06/2016.
 */
public class TipCalApp extends Application {
    private final static String ABOUT_URL = "https://github.com/neyogiry";

    public String getAboutUrl() {
        return ABOUT_URL;
    }
}
