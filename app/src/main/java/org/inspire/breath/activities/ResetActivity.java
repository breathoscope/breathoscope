package org.inspire.breath.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.inspire.breath.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResetActivity extends AppCompatActivity {

    private static final Class<?> NEXT_ACTIVITY = LoginActivity.class;

    private EditText mFirstInput;
    private EditText mSecondInput;
    private Button mResetButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        
        findViews();

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptReset()) {
                    startActivity(new Intent(ResetActivity.this, NEXT_ACTIVITY));
                }
            }
        });
    }

    private boolean attemptReset() {
        if (mFirstInput.getText().toString().equals(mSecondInput.getText().toString())) {
            String key = getResources().getString(R.string.preference_pass_hash);
            SharedPreferences preferences = getSharedPreferences(key,MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, getStringHash(mFirstInput.getText().toString()));
            editor.apply();

            // TODO reset DB

            return true;
        }
        else {
            Toast.makeText(this, "Passwords don't match please try again", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private String getStringHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(input.getBytes());
            byte[] out = md.digest();
            return new String(out);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "undef";
    }

    private void findViews() {
        this.mFirstInput = findViewById(R.id.reset_password_first);
        this.mSecondInput = findViewById(R.id.reset_password_second);

        this.mResetButton = findViewById(R.id.reset_reset_btn);
    }
}
