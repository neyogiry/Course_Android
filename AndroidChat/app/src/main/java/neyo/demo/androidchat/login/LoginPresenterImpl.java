package neyo.demo.androidchat.login;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import neyo.demo.androidchat.lib.EventBus;
import neyo.demo.androidchat.lib.GreenRobotEventBus;
import neyo.demo.androidchat.login.events.LoginEvent;
import neyo.demo.androidchat.login.ui.LoginView;

/**
 * Created by Neyo
 */
public class LoginPresenterImpl implements LoginPresenter{

    private EventBus mEvenBus;

    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginInteractor = new LoginInteractorImpl();
        this.mEvenBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        mEvenBus.register(this);
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
        mEvenBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
        }
        Log.e("LoginPresenterImpl", "onFailedToRecoverSession");
    }

    private void onSignInSuccess(){
        if (mLoginView != null){
            mLoginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess(){
        if (mLoginView != null){
            mLoginView.newUserSucess();
        }
    }

    private void onSignInError(String error){
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.newUserError(error);
        }
    }

}
