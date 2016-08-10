package neyo.demo.androidchat.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import neyo.demo.androidchat.R;
import neyo.demo.androidchat.contactlist.ContactListActivity;
import neyo.demo.androidchat.login.ui.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.editTxtEmail) EditText inputEmail;
    @BindView(R.id.editTxtPassword) EditText inputPassword;
    @BindView(R.id.btnSignin)
    Button btnSingin;
    @BindView(R.id.btnSignup)
    Button btnSignup;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.layoutMainContainer)
    RelativeLayout mRelativeLayout;

     LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.onCreate();
        mLoginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignup)
    @Override
    public void handleSignUp() {
        mLoginPresenter.registerNewUser(inputEmail.getText().toString(),
                                        inputPassword.getText().toString());
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSignIn() {
        mLoginPresenter.validateLogin(inputEmail.getText().toString(),
                                      inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String mgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(mgError);
    }

    @Override
    public void newUserSucess() {
        Snackbar.make(mRelativeLayout, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String mgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(mgError);
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
        btnSingin.setEnabled(enabled);
    }

}
