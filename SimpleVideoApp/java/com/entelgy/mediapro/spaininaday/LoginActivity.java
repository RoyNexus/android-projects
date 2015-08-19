package com.entelgy.mediapro.spaininaday;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entelgy.mediapro.spaininaday.rest.UserRequest;
import com.entelgy.mediapro.spaininaday.rest.UserResponse;
import com.entelgy.mediapro.spaininaday.util.RestUtil;
import com.entelgy.mediapro.spaininaday.util.SessionManager;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;

public class LoginActivity extends SpainInADayActivity {

    public static final String AUTH_PATH = "auth/signin";
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Set title in Action Bar
        TextView txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.iniciar_sesion);
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.rightActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        setIkarosTypeface(R.id.entrarBtn);
        setLatoRegularTypeface(R.id.usernameTxt);
        setLatoRegularTypeface(R.id.passwordTxt);
        setLatoRegularTypeface(R.id.olvidastePassBtn);

    }

    public void onLogin(View view) {

        UserRequest user = new UserRequest();
        user.setPassword(((EditText) findViewById(R.id.passwordTxt)).getText().toString());
        user.setUsername(((EditText) findViewById(R.id.usernameTxt)).getText().toString());
        setDisabledButton((Button) findViewById(R.id.entrarBtn));

        HttpLoginTask task = new HttpLoginTask();
        task.execute(user);
    }

    public void goToForgetPass(View view) {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    private class HttpLoginTask extends AsyncTask<UserRequest, Void, UserResponse> {

        @Override
        protected UserResponse doInBackground(UserRequest... params) {
            try {
                UserResponse result = (UserResponse) RestUtil.getInstance().post(RestUtil.HOST + AUTH_PATH, params[0], UserResponse.class);
                SessionManager.getInstance(getApplicationContext()).saveFullName(params[0]);
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            Log.d(TAG, "onPostExcecute result: " + userResponse);
            if (userResponse == null) {
                // Error usuario/password incorrecta
                showAlert(LoginActivity.this, R.string.aviso, R.string.datos_validos,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            },
                            null);

            } else {
                // Autenticar al usuario en memoria y mostrar la Home
                SessionManager sessionManager = SessionManager.getInstance(LoginActivity.this);
                sessionManager.saveUserSession(userResponse);
                goToHome();
            }
            setEnabledButton((Button) findViewById(R.id.entrarBtn));
        }

    }

}
