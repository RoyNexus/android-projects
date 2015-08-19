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
import com.entelgy.mediapro.spaininaday.util.InteractiveScrollView;
import com.entelgy.mediapro.spaininaday.util.RestException;
import com.entelgy.mediapro.spaininaday.util.RestUtil;
import com.entelgy.mediapro.spaininaday.util.SessionManager;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;
import com.entelgy.mediapro.spaininaday.util.UserService;
import com.entelgy.mediapro.spaininaday.util.ValidationException;

public class RegistrationActivity extends SpainInADayActivity {

    public static final String REGISTER_PATH = "auth/signup";
    public static final String TAG = "RegistrationActivity";

    TextView txtView;
    LinearLayout rightActionBar;
    LinearLayout leftActionBar;
    InteractiveScrollView scrollView;
    Button aceptoBtn;
    UserRequest datosUsuario;

    private void initRegistrationLayout() {
        setContentView(R.layout.registration);
        // Set title in Action Bar
        txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.registro);
        // Set visible/invisible buttons in Action Bar
        rightActionBar = (LinearLayout) findViewById(R.id.rightActionbar);
        rightActionBar.setVisibility(View.INVISIBLE);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        setIkarosTypeface(R.id.registrarBtn);
        setLatoRegularTypeface(R.id.nameTxt);
        setLatoRegularTypeface(R.id.surnameTxt);
        setLatoRegularTypeface(R.id.mailTxt);
        setLatoRegularTypeface(R.id.fechaNacimientoTxt);
        setLatoRegularTypeface(R.id.telefonoTxt);
        setLatoRegularTypeface(R.id.contrasenaTxt);
        setLatoRegularTypeface(R.id.textLegal);
    }

    private void fillUserFields(UserRequest user) {
        EditText nombre = (EditText) findViewById(R.id.nameTxt);
        EditText apellidos = (EditText) findViewById(R.id.surnameTxt);
        EditText cuentaDeCorreo = (EditText) findViewById(R.id.mailTxt);
        EditText fechaDeNacimiento = (EditText) findViewById(R.id.fechaNacimientoTxt);
        EditText telefono = (EditText) findViewById(R.id.telefonoTxt);

        nombre.setText(user.getFirstName());
        apellidos.setText(user.getLastName());
        cuentaDeCorreo.setText(user.getEmail());
        fechaDeNacimiento.setText(UserService.parseDateFromYYYYMMDD(user.getDateOfBirth()));
        telefono.setText(user.getPhone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRegistrationLayout();
    }

    public void onRegister(View view) {

        try {
            // Leer y validar datos del usuario
            datosUsuario = validateUserForm();
            // Mostramos pantalla condiciones legales
            setContentView(R.layout.legal_conditions);
            txtView = (TextView) findViewById(R.id.actionBarText);
            txtView.setText(R.string.registro);
            rightActionBar = (LinearLayout) findViewById(R.id.rightActionbar);
            leftActionBar = (LinearLayout) findViewById(R.id.leftActionbar);
            rightActionBar.setVisibility(View.INVISIBLE);
            leftActionBar.setVisibility(View.INVISIBLE);
            // Funcionalidad scroll down condiciones legales
            scrollView = (InteractiveScrollView) findViewById(R.id.scrollLegalTerms);
            scrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
                @Override
                public void onBottomReached() {
                    aceptoBtn = (Button) findViewById(R.id.aceptoBtn);
                    aceptoBtn.setEnabled(true);
                    aceptoBtn.setBackground(getResources().getDrawable(R.drawable.button_rectangle));
                }
            });

            // Set Typefaces
            setIkarosTypeface(R.id.actionBarText);
            setIkarosTypeface(R.id.aceptoBtn);
            setLatoRegularTypeface(R.id.leeLegalTxt);
            setLatoRegularTypeface(R.id.legalTermsTxt);
        } catch (ValidationException validationException) {
            // Mostramos error de validación
            showAlert(RegistrationActivity.this, R.string.datos_validos, validationException.getCode(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    },
                    null
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public void onAcepto(View view) {
        HttpRegisterTask task = new HttpRegisterTask();
        datosUsuario.setLegalTerm(true);
        datosUsuario.setDateOfBirth(UserService.parseDate(datosUsuario.getDateOfBirth()));
        setDisabledButton((Button) findViewById(R.id.aceptoBtn));
        task.execute(datosUsuario);
    }

    private class HttpRegisterTask extends AsyncTask<UserRequest, Void, UserResponse> {

        @Override
        protected UserResponse doInBackground(UserRequest... params) {
            UserResponse result = null;
            try {
                result = (UserResponse) RestUtil.getInstance().post(RestUtil.HOST + REGISTER_PATH, params[0], UserResponse.class);
                SessionManager.getInstance(getApplicationContext()).saveFullName(params[0]);
            } catch (RestException restException) {
                result = new UserResponse();
                result.setMessage(restException.getMessageInBody());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return result;
        }

        private boolean isNull(UserResponse user) {
            return user == null;
        }

        private boolean userGetMessageExists(UserResponse user) {
            if (user != null) {
                return user.getMessage() != null;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            Log.d(TAG, "onPostExcecute result: " + userResponse);

            if (isNull(userResponse) || userGetMessageExists(userResponse)) {
                initRegistrationLayout();
                fillUserFields(datosUsuario);
                // Seleccionar mensaje de error
                int errorId = R.string.mensaje_error_red;
                if (userGetMessageExists(userResponse)) {
                    if (userResponse.getMessage().indexOf("Username already exists") != -1) {
                        errorId = R.string.mensaje_error_username;
                    }
                }
                // Mostrar error devuelto
                showAlert(RegistrationActivity.this, R.string.error, errorId,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);
            } else {
                // Autenticar al usuario en memoria y mostrar la Home
                SessionManager sessionManager = SessionManager.getInstance(RegistrationActivity.this);
                sessionManager.saveUserSession(userResponse);
                goToHome();
            }
            setEnabledButton((Button) findViewById(R.id.aceptoBtn));
        }

    }


}
