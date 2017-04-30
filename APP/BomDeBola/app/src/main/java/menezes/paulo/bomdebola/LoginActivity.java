package menezes.paulo.bomdebola;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.listeners.Request;
import menezes.paulo.bomdebola.models.User;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.SendJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SharedPreferences mPreferences;
    private ProgressDialog mLoading;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(LoginActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        SignInButton button = (SignInButton) findViewById(R.id.login_google);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading = ProgressDialog.show(LoginActivity.this, "", getResources().getString(R.string.msg_loading), true, false);
                mGoogleApiClient.connect();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)  != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            String profile = currentPerson.getImage().getUrl();
            profile = profile.substring(0, profile.length() - 2) + 400;

            String cover = currentPerson.getCover().getCoverPhoto().getUrl();

            final User user = new User();
            user.setName(currentPerson.getName().getGivenName() + " " + currentPerson.getName().getFamilyName());
            user.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
            user.setImageCover(cover);
            user.setImageProfile(profile);
            user.setGooglePlusProfile(currentPerson.getUrl());

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    if (objectList.size() == 0) {
                        new SendJsonAsyncTask(new Gson().toJson(user), new Request() {
                            @Override
                            public void success(Boolean status) {
                                if (status) {
                                    new DownloadJsonAsyncTask(new JsonResponse() {
                                        @Override
                                        public void response(List<JSONObject> objectList) {
                                            User u = new User();
                                            u.load(objectList.get(0));

                                            login(u);

                                            mLoading.dismiss();
                                        }
                                    }).execute(URLs.API_USER_GET + user.getEmail());
                                    login(user);
                                } else {
                                    Snackbar.make(findViewById(R.id.layout), R.string.msg_error, Snackbar.LENGTH_LONG).show();
                                }

                                mLoading.dismiss();
                            }
                        }).execute(URLs.API_USER_CREATE);
                    } else {
                        User u = new User();
                        u.load(objectList.get(0));

                        login(u);

                        mLoading.dismiss();
                    }
                }
            }).execute(URLs.API_USER_GET + user.getEmail());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(connectionResult.getResolution().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void login(User user) {
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("user", new Gson().toJson(user));
        editor.commit();

        onBackPressed();
    }
}
