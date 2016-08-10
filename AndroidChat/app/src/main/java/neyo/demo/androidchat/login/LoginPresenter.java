package neyo.demo.androidchat.login;

import neyo.demo.androidchat.login.events.LoginEvent;

/**
 * Created by Neryo
 */

public interface LoginPresenter {

    void onCreate();

    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    void onEventMainThread(LoginEvent event);

}

