package org.inspire.breath.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.data.AppRoomDatabase;
import org.inspire.breath.data.MainDao;

public class LoginActivity extends AppCompatActivity {

    private static final Class<?> NEXT_ACTIVITY = PatientsActivity.class;
    ImageView mLogo;

    Button mLoginButton;
    Button mResetButton;

    EditText mUname;
    EditText mPass;

    private void findViews() {
        this.mLogo = findViewById(R.id.login_logo);
        this.mLoginButton = findViewById(R.id.login_login_btn);
        this.mResetButton = findViewById(R.id.login_reset_btn);
        this.mUname = findViewById(R.id.login_uname);
        this.mPass = findViewById(R.id.login_pass);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();


        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.this.doLogin()) {
                    startActivity(new Intent(LoginActivity.this, NEXT_ACTIVITY));
                }
                else {
                    Toast.makeText(LoginActivity.this, R.string.login_incorrect_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAccount();
            }
        });

    }

    private void resetAccount() {
        MainDao mainDao = AppRoomDatabase.getDatabase(this).mainDao();
        mainDao.deleteAllPatients();
    }

    private boolean doLogin() {
        return this.mUname.getText().toString().equals("uname");
    }
}
