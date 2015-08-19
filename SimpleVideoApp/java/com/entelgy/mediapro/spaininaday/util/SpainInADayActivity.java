package com.entelgy.mediapro.spaininaday.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.entelgy.mediapro.spaininaday.HomeActivity;
import com.entelgy.mediapro.spaininaday.MainActivity;
import com.entelgy.mediapro.spaininaday.R;
import com.entelgy.mediapro.spaininaday.rest.UserRequest;
import com.entelgy.mediapro.spaininaday.sql.Video;

import java.util.List;

public abstract class SpainInADayActivity extends Activity {

    /**
     * Finish current activity and go to previous activity in stack
     */
    public void goToBack(View view) {
        finish();
    }

    protected void goToHome() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        // Closing all the Activities
        intent = addFinishAllActivities(intent);
        startActivity(intent);
    }

    /**
     * Add finish all activities the flags
     * @param intent The Intent
     * @return The Intent with flags
     */
    public Intent addFinishAllActivities(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public Video validateVideoForm() throws ValidationException {
        Video video = new Video();
        EditText titulo = (EditText) findViewById(R.id.tituloVideoTxt);
        EditText descripcion = (EditText) findViewById(R.id.descripcionTxt);

        video.setTitulo(UserService.getString(titulo));
        video.setDescripcion(UserService.getString(descripcion));

        // Validar Título
        if (!UserService.isValidName(video.getTitulo())) {
            throw new ValidationException(R.string.codigo_error_titulo);
        }

        // Validar Descripción
        if (!UserService.isValidName(video.getDescripcion())) {
            throw new ValidationException(R.string.codigo_error_descripcion);
        }

        return video;
    }

    public UserRequest validateUserForm() throws ValidationException {
        UserRequest userRequest = new UserRequest();
        EditText nombre = (EditText) findViewById(R.id.nameTxt);
        EditText apellidos = (EditText) findViewById(R.id.surnameTxt);
        EditText cuentaDeCorreo = (EditText) findViewById(R.id.mailTxt);
        EditText fechaDeNacimiento = (EditText) findViewById(R.id.fechaNacimientoTxt);
        EditText telefono = (EditText) findViewById(R.id.telefonoTxt);
        EditText contrasena = (EditText) findViewById(R.id.contrasenaTxt);

        userRequest.setFirstName(UserService.getString(nombre));
        userRequest.setLastName(UserService.getString(apellidos));
        userRequest.setEmail(UserService.getString(cuentaDeCorreo));
        userRequest.setUsername(UserService.getString(cuentaDeCorreo));
        userRequest.setDateOfBirth(UserService.getString(fechaDeNacimiento));
        userRequest.setPhone(UserService.getString(telefono));
        userRequest.setPassword(UserService.getString(contrasena));

        // Validar Nombre
        if (!UserService.isValidName(userRequest.getFirstName())) {
            throw new ValidationException(R.string.codigo_error_nombre);
        }

        // Validar Apellidos
        if (!UserService.isValidName(userRequest.getLastName())) {
            throw new ValidationException(R.string.codigo_error_apellidos);
        }

        // Validar Cuenta de Correo
        if (!UserService.isValidEmail(userRequest.getEmail())) {
            throw new ValidationException(R.string.codigo_error_correo);
        }

        // Validar Fecha de nacimiento
        if (!UserService.isValidDateOfBirth(userRequest.getDateOfBirth())) {
            throw new ValidationException(R.string.codigo_error_fecha_nacimiento);
        }

        // Validar Teléfono
        if (!UserService.isValidPhone(userRequest.getPhone())) {
            throw new ValidationException(R.string.codigo_error_telefono);
        }

        // Validar Contraseña
        if (!UserService.isValidPassword(userRequest.getPassword())) {
            throw new ValidationException(R.string.codigo_error_contrasena);
        }

        return userRequest;
    }

    public UserRequest validateMiPerfilUserForm() throws ValidationException {
        UserRequest userRequest = new UserRequest();
        EditText nombre = (EditText) findViewById(R.id.nameEditTxt);
        EditText apellidos = (EditText) findViewById(R.id.surnameEditTxt);
        EditText cuentaDeCorreo = (EditText) findViewById(R.id.mailEditTxt);
        EditText fechaDeNacimiento = (EditText) findViewById(R.id.fechaNacimientoEditTxt);
        EditText telefono = (EditText) findViewById(R.id.telefonoEditTxt);
        EditText contrasena = (EditText) findViewById(R.id.contrasenaEditTxt);

        userRequest.setFirstName(UserService.getString(nombre));
        userRequest.setLastName(UserService.getString(apellidos));
        userRequest.setEmail(UserService.getString(cuentaDeCorreo));
        userRequest.setUsername(UserService.getString(cuentaDeCorreo));
        userRequest.setDateOfBirth(UserService.getString(fechaDeNacimiento));
        userRequest.setPhone(UserService.getString(telefono));
        userRequest.setPassword(UserService.getString(contrasena));
        userRequest.setLegalTerm(true);

        // Validar Nombre
        if (!UserService.isValidName(userRequest.getFirstName())) {
            throw new ValidationException(R.string.codigo_error_nombre);
        }

        // Validar Apellidos
        if (!UserService.isValidName(userRequest.getLastName())) {
            throw new ValidationException(R.string.codigo_error_apellidos);
        }

        // Validar Cuenta de Correo
        if (!UserService.isValidEmail(userRequest.getEmail())) {
            throw new ValidationException(R.string.codigo_error_correo);
        }

        // Validar Fecha de nacimiento
        if (!UserService.isValidDateOfBirth(userRequest.getDateOfBirth())) {
            throw new ValidationException(R.string.codigo_error_fecha_nacimiento);
        }

        // Validar Teléfono
        if (!UserService.isValidPhone(userRequest.getPhone())) {
            throw new ValidationException(R.string.codigo_error_telefono);
        }

        // Validar Contraseña
        if (userRequest.getPassword().equalsIgnoreCase(UserService.PATRON_CONTRASENA)) {
            // El usuario no ha cambiado la password, enviamos la antigua encriptada
            userRequest.setPassword(SessionManager.getInstance(getApplicationContext()).getEncryptedPassword());
        } else {
            // El usuario ha modificado la password, validamos la nueva password
            if (!UserService.isValidPassword(userRequest.getPassword())) {
                throw new ValidationException(R.string.codigo_error_contrasena);
            }
        }

        userRequest.setDateOfBirth(UserService.parseDate(userRequest.getDateOfBirth()));
        return userRequest;
    }


    public void checkUserIsNotLogged() {
        SessionManager sessionManager = SessionManager.getInstance(getApplicationContext());
        Log.d("SpainInADayActivity", "isUserLogged in checkUserIsNotLogged: " + sessionManager.isUserLogged());
        if (!sessionManager.isUserLogged()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent = addFinishAllActivities(intent);
            startActivity(intent);
        }
    }

    public void checkUserIsLogged() {
        // Check login status
        SessionManager sessionManager = SessionManager.getInstance(getApplicationContext());
        Log.d("SpainInADayActivity", "isUserLogged in checkUserIsLogged: " + sessionManager.isUserLogged());
        if (sessionManager.isUserLogged()) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent = addFinishAllActivities(intent);
            startActivity(intent);
        }
    }

    /**
     * Makes that current activity will be show in fullscreen mode, with no title
     */
    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void setIkarosTypeface(int id) {
        TextView textView= (TextView) findViewById(id);
        setIkarosTypeface(textView);
    }

    protected void setIkarosTypeface(TextView textView) {
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Ikaros.otf");
        textView.setTypeface(face);
    }

    protected void setLatoRegularTypeface(int id) {
        TextView textView= (TextView) findViewById(id);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        textView.setTypeface(face);
    }

    protected void setDisabledValueEditText(EditText editText, String value) {
        editText.setText(value);
        editText.setEnabled(false);
        editText.setBackgroundColor(getResources().getColor(R.color.custom_disabled));
    }

    protected void setDisabledEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setBackgroundColor(getResources().getColor(R.color.custom_disabled));
    }

    protected void setEnableEditText(EditText editText) {
        editText.setEnabled(true);
        editText.setBackground(getResources().getDrawable(R.drawable.text_rectangle));
    }

    protected void setDisabledButton(Button button) {
        button.setEnabled(false);
        button.setBackground(getResources().getDrawable(R.drawable.gray_button_rectangle));
    }

    protected  void setEnabledButton(Button button) {
        button.setEnabled(true);
        button.setBackground(getResources().getDrawable(R.drawable.button_rectangle));
    }

    protected void showAlert(Context context, int title, int message, DialogInterface.OnClickListener yesFunction, DialogInterface.OnClickListener noFunction) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        if (yesFunction != null) {
            alertDialog.setPositiveButton(android.R.string.yes, yesFunction);
        }

        if (noFunction != null) {
            alertDialog.setNegativeButton(android.R.string.no, noFunction);
        }

         /*
         LayoutInflater inflater = (LayoutInflater)LoginActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
         View layout = inflater.inflate(R.layout.custom_dialog, null);
         alertDialog.setView(layout);*/
        alertDialog.show();
    }

    protected static class MySpinnerAdapter<String> extends ArrayAdapter<String> {
        // Initialise custom font, for example:
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");

        // (In reality I used a manager which caches the Typeface objects)
        // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

        public MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            // no funciona: view.setTextSize(16);
            view.setTextColor(getContext().getResources().getColor(R.color.custom_gray));
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            view.setPadding(0, 30, 0, 10);
            view.setTextColor(getContext().getResources().getColor(R.color.custom_gray));
            return view;
        }
    }
}

