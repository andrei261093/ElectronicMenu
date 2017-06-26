package com.example.andreiiorga.electronicmenu.activities;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.R;
import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;
import com.example.andreiiorga.electronicmenu.asyncTasks.AuthService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText serverURL;

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        serverURL = (EditText) findViewById(R.id.serverUrl);

        Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String tableNo = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                StaticStrings.SERVER_URL = serverURL.getText().toString();

                login(tableNo, password);

            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void login(String tableNo, String password) {
        AuthService authService = new AuthService(this);
        authService.setCredentials(tableNo, password);
        authService.execute();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please Login", Toast.LENGTH_LONG).show();
    }

}

