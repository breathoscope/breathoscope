package org.inspire.breath.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.inspire.breath.R;
import org.inspire.breath.activities.ResetActivity;
import org.inspire.breath.data.AppRoomDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final Class<?> NEXT_ACTIVITY = PatientsActivity.class;
    ImageView mLogo;

    Button mLoginButton;
    Button mResetButton;

    EditText mUname; // TODO get rid of uname (probably unnecessary)
    EditText mPass;

    private void findViews() {
        this.mLogo = findViewById(R.id.login_logo);
        this.mLoginButton = findViewById(R.id.login_login_btn);
        this.mResetButton = findViewById(R.id.login_reset_btn);
        this.mUname = findViewById(R.id.login_uname);
        this.mPass = findViewById(R.id.login_pass);
    }

    private void initDb() {
        AppRoomDatabase.initDatabase(this, mPass);
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
                    initDb();
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
        startActivity(new Intent(this, ResetActivity.class));
    }

    private boolean doLogin() {

        String key = getResources().getString(R.string.preference_pass_hash);

        SharedPreferences preferences = getSharedPreferences(key, MODE_PRIVATE);
        String hash = preferences.getString(key,"unset");
        byte[] input = getHash(this.mPass.getText().toString());

        if (hash.equals("unset")) {
            System.exit(1); // lol peace out idk how this happened
        }

        if (input == null)
            return false; // hashing broke somehow (shouldn't happen but is a necessary guard)

        return new String(input).equals(hash);

    }

    private byte[] getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(input.getBytes());
            byte[] out = md.digest();
            return out;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
