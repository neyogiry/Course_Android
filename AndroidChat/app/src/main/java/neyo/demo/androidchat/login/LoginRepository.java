package neyo.demo.androidchat.login;

/**
 * Created by Neyo
 */

public interface LoginRepository {

    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();

}
