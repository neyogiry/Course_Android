package neyo.demo.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Neyo
 */
public class AndroidChatApplicattion extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
