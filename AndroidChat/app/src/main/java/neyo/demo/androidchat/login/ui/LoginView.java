package neyo.demo.androidchat.login.ui;

/**
 * Created by Neyo
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSucess();
    void newUserError(String error);

}
