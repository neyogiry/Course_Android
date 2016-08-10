package neyo.demo.androidchat.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import neyo.demo.androidchat.domain.FirebaseHelper;
import neyo.demo.androidchat.entities.User;
import neyo.demo.androidchat.lib.EventBus;
import neyo.demo.androidchat.lib.GreenRobotEventBus;
import neyo.demo.androidchat.login.events.LoginEvent;

/**
 * Created by Neyo
 */
public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper mFirebaseHelper;

    private DatabaseReference myDBReference;
    private DatabaseReference myUserReference;
    private FirebaseAuth mFrbAuth;

    public LoginRepositoryImpl() {
        this.mFirebaseHelper = FirebaseHelper.getInstance();
        this.myDBReference = mFirebaseHelper.getmDataReference();
        this.myUserReference = mFirebaseHelper.getMyUserReference();
        mFrbAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(final String email, final String password) {
        mFrbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        postEvent(LoginEvent.onSignUpSuccess);
                        signIn(email, password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    @Override
    public void signIn(final String email, final String password) {
        try {
            mFrbAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myUserReference = mFirebaseHelper.getMyUserReference();
                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    initSignIn(dataSnapshot);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                        postEvent(LoginEvent.onSignInError, databaseError.getMessage());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            postEvent(LoginEvent.onSignInError, e.getMessage());
                        }
                    });
        }catch (Exception e){
            postEvent(LoginEvent.onSignInError, e.getMessage());
        }
    }

    @Override
    public void checkSession() {
        if(mFrbAuth != null){
            myUserReference = mFirebaseHelper.getMyUserReference();
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    initSignIn(snapshot);
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                }
            });
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
        postEvent(LoginEvent.onFailedToRecoverSession);
    }

    private void initSignIn(DataSnapshot snapshot){
        User currentUser = snapshot.getValue(User.class);
        if (currentUser == null) {
            registerNewUser();
        }
        mFirebaseHelper.changeUserConnectionStatus(User.ONLINE);
        postEvent(LoginEvent.onSignInSuccess);
    }

    private void registerNewUser() {
        String email = mFirebaseHelper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User();
            //User currentUser = new User(email, true, null);
            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent (int typeEvent, String errorMessage){
        LoginEvent mLoginEvent = new LoginEvent();
        mLoginEvent.setEventType(typeEvent);
        if(errorMessage != null){
            mLoginEvent.setErrorMessage(errorMessage);
        }
        EventBus mEventBus = GreenRobotEventBus.getInstance();
        mEventBus.post(mLoginEvent);
    }

    private void postEvent (int typeEvent){
        postEvent(typeEvent, null);
    }

}
