package com.entelgy.mediapro.spaininaday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.entelgy.mediapro.spaininaday.util.CategoryUtil;
import com.entelgy.mediapro.spaininaday.util.SessionManager;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;

public class MainActivity extends SpainInADayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryUtil.getInstance();
        if (SessionManager.getInstance(getApplicationContext()).isUserLogged()) {
            checkUserIsLogged();
        } else {
            setFullScreen();
            setContentView(R.layout.main);
            // Set Typefaces
            setIkarosTypeface(R.id.loginBtn);
            setIkarosTypeface(R.id.registrarBtn);
        }
    }

    /**
     * Go to Login activity
     */
    public void goToEntra(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Go to Register activity
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
