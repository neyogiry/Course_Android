package neyo.demo.androidchat.login;

/**
 * Created by Neyo
 */

public interface LoginInteractor {

    void checkSession();

    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
