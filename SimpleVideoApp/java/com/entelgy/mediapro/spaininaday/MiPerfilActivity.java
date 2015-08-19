package com.entelgy.mediapro.spaininaday;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entelgy.mediapro.spaininaday.rest.UserRequest;
import com.entelgy.mediapro.spaininaday.rest.UserResponse;
import com.entelgy.mediapro.spaininaday.util.RestUtil;
import com.entelgy.mediapro.spaininaday.util.SessionManager;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;
import com.entelgy.mediapro.spaininaday.util.UserService;
import com.entelgy.mediapro.spaininaday.util.ValidationException;

public class MiPerfilActivity extends SpainInADayActivity {

    public static final String USERS_CRUD = "users/crud";
    public static final String USER = "/{user}";
    public static final String TAG = "MiPerfilActivity";

    private EditText nombre;
    private EditText apellidos;
    private EditText cuentaCorreo;
    private EditText fechaDeNacimiento;
    private EditText telefono;
    private EditText contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_perfil);
        // Set title in Action Bar
        TextView txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.mi_perfil);
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.rightActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        actionbarLayout = (LinearLayout) findViewById(R.id.editPenActionbar);
        actionbarLayout.setVisibility(View.VISIBLE);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        setIkarosTypeface(R.id.cerrarSesionBtn);
        setLatoRegularTypeface(R.id.nameEditLbl);
        setLatoRegularTypeface(R.id.surnameEditLbl);
        setLatoRegularTypeface(R.id.mailEditLbl);
        setLatoRegularTypeface(R.id.fechaNacimientoEditLbl);
        setLatoRegularTypeface(R.id.telefonoEditLbl);
        setLatoRegularTypeface(R.id.contrasenaEditLbl);

        setLatoRegularTypeface(R.id.nameEditTxt);
        setLatoRegularTypeface(R.id.surnameEditTxt);
        setLatoRegularTypeface(R.id.mailEditTxt);
        setLatoRegularTypeface(R.id.fechaNacimientoEditTxt);
        setLatoRegularTypeface(R.id.telefonoEditTxt);
        setLatoRegularTypeface(R.id.contrasenaEditTxt);

        HttpGetMiPerfilTask task = new HttpGetMiPerfilTask();
        UserResponse user = new UserResponse();
        user.set_id(SessionManager.getInstance(getApplicationContext()).getLoggedUserId());
        task.execute(user);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Enable EditText
        nombre = (EditText) findViewById(R.id.nameEditTxt);
        apellidos = (EditText) findViewById(R.id.surnameEditTxt);
        cuentaCorreo = (EditText) findViewById(R.id.mailEditTxt);
        fechaDeNacimiento = (EditText) findViewById(R.id.fechaNacimientoEditTxt);
        telefono = (EditText) findViewById(R.id.telefonoEditTxt);
        contrasena = (EditText) findViewById(R.id.contrasenaEditTxt);
    }

    public void onLogout(View view) {
        SessionManager sessionManager = SessionManager.getInstance(MiPerfilActivity.this);
        sessionManager.deleteUserSession();

        Intent intent = new Intent(this, MainActivity.class);
        intent = addFinishAllActivities(intent);
        startActivity(intent);
    }

    public void goToEditProfile(View view) {
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.editPenActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        actionbarLayout = (LinearLayout) findViewById(R.id.saveActionbar);
        actionbarLayout.setVisibility(View.VISIBLE);
        // Disable all edit texts
        setEnableEditText(nombre);
        setEnableEditText(apellidos);
        setEnableEditText(cuentaCorreo);
        setEnableEditText(fechaDeNacimiento);
        setEnableEditText(telefono);
        setEnableEditText(contrasena);
    }

    public void goToSaveProfile(View view) {
        UserRequest user;
        try {
            user = validateMiPerfilUserForm();
        } catch (ValidationException validationException) {
            // Mostramos error de validación
            showAlert(MiPerfilActivity.this, R.string.datos_validos, validationException.getCode(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    },
                    null
            );
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.editPenActionbar);
        actionbarLayout.setVisibility(View.VISIBLE);
        actionbarLayout = (LinearLayout) findViewById(R.id.saveActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        // Enable all edit texts
        setDisabledEditText(nombre);
        setDisabledEditText(apellidos);
        setDisabledEditText(cuentaCorreo);
        setDisabledEditText(fechaDeNacimiento);
        setDisabledEditText(telefono);
        setDisabledEditText(contrasena);

        HttpPutMiPerfilTask task = new HttpPutMiPerfilTask();
        task.setUserId(SessionManager.getInstance(getApplicationContext()).getLoggedUserId());
        task.execute(user);
    }

    private class HttpPutMiPerfilTask extends AsyncTask<UserRequest, Void, UserRequest> {

        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        protected UserRequest doInBackground(UserRequest... params) {
            try {
                UserRequest result = (UserRequest) RestUtil.getInstance().put(RestUtil.HOST + USERS_CRUD + USER,
                        params[0], getUserId());
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(UserRequest user) {
            Log.d(TAG, "onPostExcecute result: " + user);
            if (user == null) {
                // Error recuperando datos de usuario
                showAlert(MiPerfilActivity.this, R.string.error, R.string.mensaje_error_red,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);

            } else {
                // Ha habido respuesta OK
            }
        }

    }

    private class HttpGetMiPerfilTask extends AsyncTask<UserResponse, Void, UserRequest> {

        @Override
        protected UserRequest doInBackground(UserResponse... params) {
            try {
                UserRequest result = (UserRequest) RestUtil.getInstance().get(RestUtil.HOST + USERS_CRUD + USER,
                                UserRequest.class, ((UserResponse) params[0]).get_id());
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(UserRequest user) {
            Log.d(TAG, "onPostExcecute result: " + user);
            // setEnabledButton((Button) findViewById(R.id.entrarBtn));
            if (user == null) {
                // Error recuperando datos de usuario
                showAlert(MiPerfilActivity.this, R.string.error, R.string.mensaje_error_red,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);

            } else {
                // Mostrar datos de Mi Perfil
                setDisabledValueEditText(nombre, user.getFirstName());
                setDisabledValueEditText(apellidos, user.getLastName());
                setDisabledValueEditText(cuentaCorreo, user.getEmail());
                setDisabledValueEditText(fechaDeNacimiento, UserService.parseDateFromYYYYMMDD(user.getDateOfBirth()));
                setDisabledValueEditText(telefono, user.getPhone());
                setDisabledValueEditText(contrasena, UserService.PATRON_CONTRASENA);
                SessionManager.getInstance(getApplicationContext()).saveEncryptedPassword(user.getPassword());
            }
        }
    }
}
