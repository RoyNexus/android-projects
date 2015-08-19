package com.entelgy.mediapro.spaininaday;

import android.content.DialogInterface;
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
import com.entelgy.mediapro.spaininaday.util.RestException;
import com.entelgy.mediapro.spaininaday.util.RestUtil;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;

public class PasswordActivity extends SpainInADayActivity {

    public static final String AUTH_FORGOT = "auth/forgot";
    public static final String TAG = "PasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_contrasena);

        // Set title in Action Bar
        TextView txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.recordar_contrasena);
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.leftActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        setIkarosTypeface(R.id.restablecerBtn);
        setLatoRegularTypeface(R.id.mailEnviarPassTxt);
    }

    public void onSendPassword(View view) {
        setDisabledButton((Button) findViewById(R.id.restablecerBtn));
        HttpForgotPasswordTask task = new HttpForgotPasswordTask();
        UserRequest user = new UserRequest();
        EditText username = (EditText) findViewById(R.id.mailEnviarPassTxt);
        user.setUsername(username.getText().toString());
        task.execute(user);
    }

    private class HttpForgotPasswordTask extends AsyncTask<UserRequest, Void, UserResponse> {

        @Override
        protected UserResponse doInBackground(UserRequest... params) {
            UserResponse result = null;
            try {
                result = (UserResponse) RestUtil.getInstance().post(RestUtil.HOST + AUTH_FORGOT, params[0], UserResponse.class);
                return result;
            } catch (RestException restException) {
                result = new UserResponse();
                result.setMessage(restException.getMessageInBody());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return result;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            Log.d(TAG, "onPostExcecute result: " + userResponse);
            setEnabledButton((Button) findViewById(R.id.restablecerBtn));
            if (userResponse == null) {
                // Error de red
                showAlert(PasswordActivity.this, R.string.error, R.string.mensaje_error_red,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);

            } else {
                if (userResponse.getMessage().indexOf("An email has been sent") != -1) {
                    // Se ha enviado correctamente el email de restablecer password
                    showAlert(PasswordActivity.this, R.string.aviso, R.string.mensaje_confirmacion_forgot_pass,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Cerramos la pantalla de Restablecer Password
                                    finish();
                                }
                            },
                            null);
                } else {
                    // La cuenta indicada no existe
                    showAlert(PasswordActivity.this, R.string.error, R.string.mensaje_error_cuenta_no_existe,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            },
                            null);
                }

            }
        }
    }
}
